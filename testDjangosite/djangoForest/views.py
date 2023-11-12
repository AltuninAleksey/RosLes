import datetime
import simplejson
from django.core.files.images import ImageFile
from django.db.models import F
from django.http import JsonResponse, HttpResponse, FileResponse
from rest_framework import generics, status
from PIL import Image
import io
from rest_framework.generics import ListAPIView
from rest_framework.parsers import FileUploadParser, MultiPartParser, FormParser
from rest_framework.response import Response
from rest_framework import viewsets, permissions
from rest_framework.authtoken.models import Token
from rest_framework.views import APIView
from rest_framework.permissions import IsAuthenticated
from django.contrib.auth.hashers import make_password, check_password

from testDjangosite.settings import BASE_DIR
from rest_framework.renderers import MultiPartRenderer, JSONRenderer
from testDjangosite.settings import MEDIA_URL, MEDIA_ROOT
from django.core.files import File

from djangoForest.serializers import *
from collections import namedtuple


class TestAPIView(generics.ListAPIView):
    def get(self, request):
        lst = Profile.objects.all()
        return Response({'': ProfileSerializer(lst, many=True).data})


class VerifyTokenView(APIView):
    permission_classes = [IsAuthenticated, ]

    def get(self, request, **kwargs):
        return Response({"code": status.HTTP_200_OK}, status = status.HTTP_200_OK)

class ProfileView(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            try:
                lst = Profile.objects.get(pk=kwargs['pk'])
                return Response({'get': ProfileSerializer(lst).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        lst = Profile.objects.all()
        return Response({'get': ProfileSerializer(lst, many=True).data})

    def post(self, request):
        serializer = ProfileSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        try:
            instance = Profile.objects.get(pk=kwargs['pk'])
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = ProfileSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class ListView(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            try:
                lst = List.objects.get(pk=kwargs['pk'])
                return Response({'get': ListSerializer(lst).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        lst = List.objects.all()
        return Response({'get': ListSerializer(lst, many=True).data})
        # return render(request, 'listview/list.html', {
        #     "post": lst
        # })

    def post(self, request):
        serializer = ListSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        if kwargs:
            try:
                instance = List.objects.get(pk=kwargs['pk'])
                serealizer = ListSerializer(data=request.data, instance=instance)
                serealizer.is_valid(raise_exception=True)
                serealizer.save()
                return Response({"put": status.HTTP_200_OK})
            except:
                return Response({"Объект с данным id не найден"})

        for i in range(len(request.data['data'])):
            if request.data['data'][i]['mark_update'] == 1:
                instance = List.objects.get(id=request.data['data'][i]["id"])
                serealizer = ListSerializer(data=request.data["data"][i], instance=instance)
                serealizer.is_valid(raise_exception=True)
                serealizer.save()
            elif request.data['data'][i]['mark_update'] == 2:
                serializer = ListSerializer(data=request.data['data'][i])
                serializer.is_valid(raise_exception=True)
                serializer.save()
                lst = List.objects.get(id=serializer.data['id'])
                lst.mark_update = 0
                lst.save()
        return Response({"put": status.HTTP_200_OK})

    def delete(self, *args, **kwargs):
        try:
            lst = List.objects.get(id=kwargs['pk'])
        except:
            return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                            status=status.HTTP_404_NOT_FOUND)
        lst.delete()
        return Response({"code": status.HTTP_200_OK}, status=status.HTTP_200_OK)

class GpsView(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            try:
                lst = GPS.objects.get(pk=kwargs['pk'])
                return Response({'get': GPSSerializer(lst).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        lst = GPS.objects.all()
        return Response({'get': GPSSerializer(lst, many=True).data})

    def post(self, request):
        serializer = GPSSerializer(data=request.data)
        if not serializer.is_valid():
            return Response({"error": status.HTTP_400_BAD_REQUEST,
                             "error_text": serializer.errors[next(iter(serializer.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        try:
            instance = GPS.objects.get(pk=kwargs['pk'])
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = GPSSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})

    def delete(self, *args, **kwargs):
        try:
            lst = GPS.objects.get(id = kwargs['pk'])
        except:
            return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                            status=status.HTTP_404_NOT_FOUND)
        lst.delete()
        return Response({"code": status.HTTP_200_OK}, status=status.HTTP_200_OK)


class GpsBySampleView(ListAPIView):

    def get(self, *args, **kwargs):
        try:
            lst = GPS.objects.filter(id_sample__id_list_region = kwargs['id_list_region'], flag_center = 1)
        except:
            return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id sample"},
                            status=status.HTTP_404_NOT_FOUND)
        return Response(GPSSerializer(lst, many=True).data)


    # def put(self, request, *args, **kwargs):
    #     try:
    #         instance = GPS.objects.get(pk=kwargs['pk'])
    #     except:
    #         return Response({"error": "Объект с данным id не найден"})
    #
    #     serealizer = GPSSerializer(data=request.data, instance=instance)
    #     serealizer.is_valid(raise_exception=True)
    #     serealizer.save()
    #     return Response({"put": serealizer.data})


class ListRegionView(generics.ListCreateAPIView):
    model = ListRegion

    def get(self, request, *args, **kwargs):
        if kwargs:
            try:
                lst = ListRegion.objects.get(pk=kwargs['pk'])
                ser_lst = ListRegionSerializerId(lst).data
                if FieldCard.objects.filter(id_list_region = kwargs['pk']).exists():
                    lst_field = FieldCard.objects.filter(id_list_region=kwargs['pk']).values("id")
                    ser_lst.update({"id_field_card": lst_field[0]['id']})
                else:
                    ser_lst.update({"id_field_card": 0})
                if DescriptionRegion.objects.filter(id_list_region=kwargs['pk']).exists():
                    lst_desc = DescriptionRegion.objects.filter(id_list_region=kwargs['pk']).values("id")
                    ser_lst.update({"id_desc": lst_desc[0]['id']})
                else:
                    ser_lst.update({"id_desc": 0})
                return Response(ser_lst)
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        lst = ListRegion.objects.all()
        return Response({"get":ListRegionSerializer(lst, many=True).data})

    def post(self, request):
        serializer = ListRegionSerializer(data=request.data)
        if not serializer.is_valid():
            return Response({"error": status.HTTP_400_BAD_REQUEST,
                             "error_text": serializer.errors[next(iter(serializer.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        serializer.save()
        region = ListRegion.objects.get(id=serializer.data['id'])
        field = FieldCard(id_list_region = region)
        field.save()
        desc = DescriptionRegion(id_list_region = region)
        desc.save()
        return Response({'code': status.HTTP_201_CREATED})

    def put(self, request, *args, **kwargs):
        if kwargs:
            try:
                instance = ListRegion.objects.get(pk=kwargs['pk'])
                serealizer = ListRegionSerializer(data=request.data, instance=instance)
                serealizer.is_valid(raise_exception=True)
                serealizer.save()
                return Response({"put": status.HTTP_200_OK}, status=status.HTTP_200_OK)
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                         status=status.HTTP_404_NOT_FOUND)
        for i in range(len(request.data['data'])):
            if request.data['data'][i]['mark_update'] == 1:
                instance = ListRegion.objects.get(id=request.data['data'][i]["id"])
                serealizer = ListRegionSerializer(data=request.data["data"][i], instance=instance)
                serealizer.is_valid(raise_exception=True)
                serealizer.save()
            elif request.data['data'][i]['mark_update'] == 2:
                print(request.data['data'][i])
                serializer = ListRegionSerializer(data=request.data['data'][i])
                serializer.is_valid(raise_exception=True)
                serializer.save()
                lst = ListRegion.objects.get(id=serializer.data['id'])
                field = FieldCard(id_list_region=lst)
                field.save()
                desc = DescriptionRegion(id_list_region=lst)
                desc.save()
                lst.mark_update = 0
                lst.save()
        return Response({"put": status.HTTP_200_OK}, status=status.HTTP_200_OK)

    def delete(self, *args, **kwargs):
        try:
            lst = ListRegion.objects.get(id=kwargs['pk'])
        except:
            return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                            status=status.HTTP_404_NOT_FOUND)
        lst.delete()
        return Response({"code": status.HTTP_200_OK}, status=status.HTTP_200_OK)

class ListRegionViewUpdate(ListView):

    def put(self, request, *args, **kwargs):
        try:
            instance = ListRegion.objects.get(pk=kwargs['pk'])
        except:
            return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                            status=status.HTTP_404_NOT_FOUND)

        serealizer = ListRegionUpdateSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"code": status.HTTP_200_OK})


class ListRegionByProfileView(ListView):

    def get(self, *args, **kwargs):
        return Response({"get": ListRegionSerializer(ListRegion.objects.filter(id_profile = kwargs['pk_profile']), many=True).data})


class SampleView(generics.ListCreateAPIView):

    def get_serializer_class(self):
        return SampleSerializer

    
    def get(self, request, **kwargs):
        if kwargs:
            try:
                lst = Sample.objects.get(pk=kwargs['pk'])
                return Response({'get': SampleSerializerId(lst).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        lst = Sample.objects.all()
        #         # return Response({'posts': list(lst) })
        return Response({'get': SampleSerializer(lst, many=True).data})

    def post(self, request):
        serializer = SampleSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': status.HTTP_201_CREATED}, status=status.HTTP_201_CREATED)

    def put(self, request, *args, **kwargs):
        if kwargs:
            try:
                instance = Sample.objects.get(pk=kwargs['pk'])
                serealizer = SampleSerializer(data=request.data, instance=instance)
                serealizer.is_valid(raise_exception=True)
                serealizer.save()
                return Response({"put": status.HTTP_200_OK})
            except:
                return Response({"Объект с данным id не найден"})

        for i in range(len(request.data['data'])):
            if request.data['data'][i]['mark_update'] == 1:
                instance = Sample.objects.get(id=request.data['data'][i]["id"])
                serealizer = SampleSerializer(data=request.data["data"][i], instance=instance)
                serealizer.is_valid(raise_exception=True)
                serealizer.save()
            elif request.data['data'][i]['mark_update'] == 2:
                # request.data['data'][i]['mark_update'].update('mark_update: 0')
                serializer = SampleSerializer(data=request.data['data'][i])
                serializer.is_valid(raise_exception=True)
                serializer.save()
                lst = Sample.objects.get(id=serializer.data['id'])
                lst.mark_update = 0
                lst.save()
        return Response({"put": status.HTTP_200_OK})

    def delete(self, *args, **kwargs):
        try:
            lst = Sample.objects.get(id=kwargs['pk'])
        except:
            return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                            status=status.HTTP_404_NOT_FOUND)
        lst.delete()
        return Response({"code": status.HTTP_200_OK}, status=status.HTTP_200_OK)

class PostView(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            try:
                lst = Post.objects.get(pk=kwargs['pk'])
                return Response({'get': PostSerializer(lst).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        lst = Post.objects.all()
        return Response({'get': PostSerializer(lst, many=True).data})

    def post(self, request):
        serializer = PostSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        try:
            instance = Post.objects.get(pk=kwargs['pk'])
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = PostSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})

class WorkingBreedView(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            try:
                lst = WorkingBreeds.objects.get(pk=kwargs['pk'])
                return Response({'get': WorkingBreedsSerializer(lst).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        lst = WorkingBreeds.objects.all()
        return Response({'get': WorkingBreedsSerializer(lst, many=True).data})

    def post(self, request):
        serializer = WorkingBreedsSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        try:
            instance = WorkingBreeds.objects.get(pk=kwargs['pk'])
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = WorkingBreedsSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})

    def delete(self, *args, **kwargs):
        try:
            lst = WorkingBreeds.objects.get(id = kwargs['pk'])
        except:
            return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                            status=status.HTTP_404_NOT_FOUND)
        lst.delete()
        return Response({"code": status.HTTP_200_OK}, status=status.HTTP_200_OK)



class SubjectRFview(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            try:
                lst = SubjectRF.objects.get(pk=kwargs['pk'])
                return Response({'get': SubjectRFSerializer(lst).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        lst = SubjectRF.objects.all()
        return Response({'get': SubjectRFSerializer(lst, many=True).data})

    def post(self, request):
        serializer = SubjectRFSerializer(data=request.data)
        if not serializer.is_valid():
            return Response({"error": status.HTTP_400_BAD_REQUEST,
                             "error_text": serializer.errors[next(iter(serializer.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        serializer.save()
        return Response({'code': status.HTTP_201_CREATED}, status=status.HTTP_201_CREATED)

    def put(self, request, *args, **kwargs):
        try:
            instance = SubjectRF.objects.get(pk=kwargs['pk'])
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = SubjectRFSerializer(data=request.data, instance=instance)
        if not serealizer.is_valid():
            return Response({"error": status.HTTP_400_BAD_REQUEST,
                             "error_text": serealizer.errors[next(iter(serealizer.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        serealizer.save()
        return Response({"put": status.HTTP_200_OK})


class RoleView(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            try:
                lst = Role.objects.get(pk=kwargs['pk'])
                return Response({'get': RoleSerializer(lst).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        lst = Role.objects.all()
        return Response({'get': RoleSerializer(lst, many=True).data})

    def post(self, request, **kwargs):
        serializer = RoleSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        try:
            instance = Role.objects.get(pk=kwargs['pk'])
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = RoleSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class ReproductionView(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            try:
                lst = Reproduction.objects.get(pk=kwargs['pk'])
                return Response({'get': ReproductionSerializer(lst).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        lst = Reproduction.objects.all()
        return Response({'get': ReproductionSerializer(lst, many=True).data})

    def post(self, request):
        serializer = ReproductionSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        try:
            instance = Reproduction.objects.get(pk=kwargs['pk'])
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = ReproductionSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class ForestlyView(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            try:
                lst = Forestly.objects.get(pk=kwargs['pk'])
                return Response({'get': ForestlySerializer(lst).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        lst = Forestly.objects.all()
        return Response({'get': ForestlySerializer(lst, many=True).data})

    def post(self, request):
        serializer = ForestlySerializer(data=request.data)
        if not serializer.is_valid():
            return Response({"error": status.HTTP_400_BAD_REQUEST,
                             "error_text": serializer.errors[next(iter(serializer.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        serializer.save()
        return Response({'code': status.HTTP_201_CREATED}, status=status.HTTP_201_CREATED)

    def put(self, request, *args, **kwargs):
        try:
            instance = Forestly.objects.get(pk=kwargs['pk'])
        except:
            return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                            status=status.HTTP_404_NOT_FOUND)
        serealizer = ForestlySerializer(data=request.data, instance=instance)
        if not serealizer.is_valid():
            return Response({"error": status.HTTP_400_BAD_REQUEST,
                             "error_text": serealizer.errors[next(iter(serealizer.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        serealizer.save()
        return Response({"put": status.HTTP_200_OK})


class DistrictForestlyView(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            try:
                lst = DistrictForestly.objects.get(pk=kwargs['pk'])
                return Response({'get': DistrictForestlySerializer(lst).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        lst = DistrictForestly.objects.all()
        return Response({'get': DistrictForestlySerializer(lst, many=True).data})

    def post(self, request):
        serializer = DistrictForestlySerializer(data=request.data)
        if not serializer.is_valid():
            return Response({"error": status.HTTP_400_BAD_REQUEST,
                             "error_text": serializer.errors[next(iter(serializer.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        serializer.save()
        return Response({'post': status.HTTP_201_CREATED}, status=status.HTTP_201_CREATED)

    def put(self, request, *args, **kwargs):
        try:
            instance = DistrictForestly.objects.get(pk=kwargs['pk'])
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = DistrictForestlySerializer(data=request.data, instance=instance)
        if not serealizer.is_valid():
            return Response({"error": status.HTTP_400_BAD_REQUEST,
                             "error_text": serealizer.errors[next(iter(serealizer.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        serealizer.save()
        return Response({"put": serealizer.data})


class QuarterView(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            try:
                lst = Quarter.objects.get(pk=kwargs['pk'])
                return Response({'get': QuarterSerializer(lst).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        lst = Quarter.objects.all()
        return Response({'get':QuarterSerializer(lst, many=True).data})

    def post(self, request):
        serializer = QuarterSerializer(data=request.data)
        if not serializer.is_valid():
            return Response({"error": status.HTTP_400_BAD_REQUEST,
                             "error_text": serializer.errors[next(iter(serializer.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        serializer.save()
        return Response({'post': status.HTTP_201_CREATED}, status=status.HTTP_201_CREATED)

    def put(self, request, *args, **kwargs):
        try:
            instance = Quarter.objects.get(pk=kwargs['id'])
        except:
            return Response({"error": "Объект с данным id не найден"})
        serealizer = QuarterSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": status.HTTP_200_OK}, status= status.HTTP_200_OK)


class UndergrowthView(APIView):

    def get(self, *args, **kwargs):
        if kwargs:
            try:
                lst = Undergrowth.objects.get(pk=kwargs['pk'])
                return Response(UndergrowthSerializer(lst).data)
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        return Response({"get":UndergrowthSerializer(Undergrowth.objects.all(), many=True).data})


class UndergrowthByDefaultView(APIView):

    def get(self, *args, **kwargs):
        return Response({"get":UndergrowthByDefaultSerializer(UndergrowthByDefault.objects.all(), many=True).data})


class BreedView(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            try:
                lst = Breed.objects.get(pk=kwargs['pk'])
                return Response({'get': BreedSerializer(lst).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        lst = Breed.objects.all()
        return Response({'get': BreedSerializer(lst, many=True).data})

    def post(self, request):
        serializer = BreedSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        try:
            instance = Breed.objects.get(pk=kwargs['pk'])
        except:
            return Response({"error": "Объект с данным id не найден"})
        serealizer = BreedSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class BranchesView(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            try:
                lst = Branches.objects.get(pk=kwargs['pk'])
                return Response({'get': BranchesSerializer(lst).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        lst = Branches.objects.all()
        return Response({'get': BranchesSerializer(lst, many=True).data})

    def post(self, request):
        serializer = BranchesSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        try:
            instance = Branches.objects.get(pk=kwargs['pk'])
        except:
            return Response({"error": "Объект с данным id не найден"})
        serealizer = BranchesSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class SchemaMixingBreedsView(ListAPIView):

    def get(self, request, *args, **kwargs):
        if kwargs:
            try:
                return Response({"get": SchemaMixingBreedsSerializer(SchemaMixingBreeds.objects.get(id=kwargs["pk"])).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        return Response({"get": SchemaMixingBreedsSerializer(SchemaMixingBreeds.objects.all(), many=True).data})


class Point7TableView(ListAPIView):

    def get(self, request, *args, **kwargs):
        if kwargs:
            try:
                print(kwargs['pk'])
                lst = List.objects.filter(id_sample__id_list_region = kwargs["pk"]).values(
                    "age", "ratio_composition", "avg_height", "avg_diameter", "count_of_plants", "id_breed")
                # print(lst)
                return Response(lst)
                # return Response({"get": Point7TableSerializer(point7Table.objects.get(id=kwargs["pk"])).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        return Response({"get": Point7TableSerializer(point7Table.objects.all(), many=True).data})


class Point7TableSaplingView(ListAPIView):

    def get(self, request, *args, **kwargs):
        if kwargs:
            from staticpy.calculate_ratio_composition import calculate_coeff
            try:
                lst = List.objects.filter(id_sample__id_list_region=kwargs["pk"]).values(
                    "age", "avg_height", "avg_diameter", "count_of_plants", "id_breed")
                # print(lst)
                print(calculate_coeff(lst))
                return Response(calculate_coeff(lst))
                # return Response({"get": Point7TableSaplingSerializer(point7Table2Sapling.objects.get(id=kwargs["pk"])).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        return Response({"get": Point7TableSaplingSerializer(point7Table2Sapling.objects.all(), many=True).data})


class PurposeOfForestsView(ListAPIView):

    def get(self, request, *args, **kwargs):
        if kwargs:
            try:
                return Response({"get": PurposeOfForestsSerializer(PurposeOfForests.objects.get(id=kwargs["pk"])).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        return Response({"get": PurposeOfForestsSerializer(PurposeOfForests.objects.all(), many=True).data})


class ForestProtectionCategoryView(ListAPIView):

    def get(self, request, *args, **kwargs):
        if kwargs:
            try:
                return Response({"get": ForestProtectionCategorySerializer(
                    ForestProtectionCategory.objects.get(id=kwargs["pk"])).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        return Response({"get": ForestProtectionCategorySerializer(
            ForestProtectionCategory.objects.all(), many=True).data})


class CategoryOfForestFundLandsView(ListAPIView):

    def get(self, request, *args, **kwargs):
        if kwargs:
            try:
                return Response({"get": CategoryOfForestFundLandsSerializer(
                    CategoryOfForestFundLands.objects.get(id=kwargs["pk"])).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        return Response({"get": CategoryOfForestFundLandsSerializer(
            CategoryOfForestFundLands.objects.all(), many=True).data})


class MethodOfReforestationView(ListAPIView):

    def get(self, request, *args, **kwargs):
        if kwargs:
            try:
                return Response({"get": MethodOfReforestationSerializer(
                    MethodOfReforestation.objects.get(id=kwargs["pk"])).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        return Response({"get": MethodOfReforestationSerializer(
            MethodOfReforestation.objects.all(), many=True).data})


class TypeForestGrowingConditionsView(ListAPIView):

    def get(self, request, *args, **kwargs):
        if kwargs:
            try:
                return Response({"get": TypeForestGrowingConditionsSerializer(
                    TypeForestGrowingConditions.objects.get(id=kwargs["pk"])).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        return Response({"get": TypeForestGrowingConditionsSerializer(
            TypeForestGrowingConditions.objects.all(), many=True).data})


class EconomyView(ListAPIView):

    def get(self, request, *args, **kwargs):
        if kwargs:
            try:
                return Response({"get": EconomySerializer(
                    Economy.objects.get(id=kwargs["pk"])).data})
            except:
                return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                status=status.HTTP_404_NOT_FOUND)
        return Response({"get": EconomySerializer(
            Economy.objects.all(), many=True).data})


class AllForestlyViewSet(viewsets.ViewSet):
    def list(self, request):
        AllForest = namedtuple('Forestly', ('district_forestly', 'forestly', 'subjectrf', 'quarter'))
        lst = AllForest(
            district_forestly=DistrictForestly.objects.all(),
            forestly=Forestly.objects.all(),
            subjectrf=SubjectRF.objects.all(),
            quarter=Quarter.objects.all()
        )
        serializer = AllForestSerializer(lst)
        return Response(serializer.data)


class GetDocumentListData(viewsets.ViewSet):
    def list(self, request, *args, **kwargs):
        pk = kwargs.get('pk')
        lst = List.objects.filter(id_sample=kwargs.get('pk'), id_undergrowth__isnull = True)
        lst2 = List.objects.filter(id_sample=kwargs.get('pk'), id_undergrowth__isnull = False)
        state = Sample.objects.filter(pk=pk)
        gps = GPS.objects.filter(id_sample=pk)

        return JsonResponse({'list_data': GetDocumentListSerializer(lst, many=True).data,
                             # 'non_null_list_data': GetDocumentListSerializer(lst2, many=True).data,
                             'non_null_list_data': GetDocumentListSerializerNonNull(lst2, many=True).data,
                             'post_data': GetFromSampleProfileSerializer(state, many=True).data,
                             'gps_data': GetGPS(gps, many=True).data}, safe=False)


class GetSampleListData(viewsets.ViewSet):
    def list(self, requests, *args, **kwargs):
        pk = kwargs.get('pk')
        lst = Sample.objects.filter(pk=pk)
        return JsonResponse({'Sample_data': GetSampleListDataSerializer(lst, many=True).data}, safe=False)


class GetAllSampleListData(viewsets.ViewSet):
    def list(self, request, *args, **kwargs):
        # lst = Sample.objects.all().filter(id_list_region = None).order_by('-id')
        lst = Sample.objects.all()
        return JsonResponse({'data': GetAllSampleListDataSerializer(lst, many=True).data}, safe=False)


class GetAllListRegionData(viewsets.ViewSet):

    def list(self, request, **kwargs):
        lst = ListRegion.objects.all()
        return JsonResponse({'data': GetAllListRegionDataSerializer(lst, many=True).data}, safe=False)

# id undergrowth = 0
class CreateSampleAndOther(ListAPIView):

    def post(self, request, **kwargs):
        serializer = SampleSerializer(data=request.data['sample'])
        serializer.is_valid(raise_exception=True)
        serializer.save()
        sample_dict = {'id_sample': serializer.data['id']}
        request.data['sample'].update(sample_dict)
        i = 0
        j = 0
        if len(request.data['list_data']) != 0:
            while i < len(request.data['list_data']):
                request.data['list_data'][i].update(sample_dict)
                i += 1
            serializer_list = ListSerializer(data=request.data['list_data'], many=True)
            serializer_list.is_valid(raise_exception=True)
            serializer_list.save()
        else:
            serializer_list = ListSerializer(data="")
            serializer_list.is_valid(raise_exception=False)
        if len(request.data['gps_data']) != 0:
            while j < len(request.data['gps_data']):
                request.data['gps_data'][j].update(sample_dict)
                j += 1
            serializer_gps = GPSSerializer(data=request.data['gps_data'], many=True)
            serializer_gps.is_valid(raise_exception=True)
            serializer_gps.save()
        else:
            serializer_gps = GPSSerializer(data="")
            serializer_gps.is_valid(raise_exception=False)
        # if len(request.data['profile_data']) != 0:
        #     for k in request.data['profile_data']:
        #         serializer_profile = ProfileSerializer(data=k)
        #         if not serializer_profile.is_valid():
        #             return Response({"error + profile": status.HTTP_400_BAD_REQUEST,
        #                              "error_text": serializer_profile.errors[next(iter(serializer_profile.errors))][0]},
        #                             status=status.HTTP_400_BAD_REQUEST)
        return Response({"code": status.HTTP_201_CREATED}, status=status.HTTP_201_CREATED)

    def put(self, request, *args, **kwargs):
        try:
            instance = Sample.objects.get(pk=request.data['sample']['id'])
        except:
            return Response({"error": "Объект с данным id не найден"})
        serializer = SampleSerializer(data=request.data['sample'], instance=instance)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        id_sample = serializer.data['id']
        if len(request.data['list_data']) != 0:
            for i in request.data['list_data']:
                try:
                    instance_list = List.objects.get(pk=i['id'])
                    serializer_list = ListSerializer(data=i, instance=instance_list)
                except:
                    serializer_list = ListSerializer(data=i)
                serializer_list.is_valid()
                serializer_list.save()
        if len(request.data['gps_data']) != 0:
            for j in request.data['gps_data']:
                try:
                    instance_gps = GPS.objects.get(pk=j['id'])
                    serializer_gps = GPSSerializer(data=j, instance=instance_gps)
                except:
                    serializer_gps = GPSSerializer(data=j)
                serializer_gps.is_valid()
                serializer_gps.save()
        # изменить профиль
        # if len(request.data['profile_data']) != 0:
        #     for k in request.data['profile_data']:
        #         profile_instance = Profile.objects.get(pk=k['id'])
        #         serializer_profile = ProfileSerializer(data=k, instance=profile_instance)
        #         if not serializer_profile.is_valid():
        #             return Response({"error + profile": status.HTTP_400_BAD_REQUEST,
        #                              "error_text": serializer_profile.errors[next(iter(serializer_profile.errors))][0]},
        #                             status=status.HTTP_400_BAD_REQUEST)
        #         serializer_profile.save()
        if len(request.data['non_null_list_data']) != 0:
            for non in request.data['non_null_list_data']:
                non.update({"id_sample": f"{id_sample}"})
                try:
                    instance_list = List.objects.get(pk=non['id'])
                    serializer_list_non = ListSerializer(data=non, instance=instance_list)
                except:
                    serializer_list_non = ListSerializer(data=non)
                if not serializer_list_non.is_valid():
                    return Response({"error": status.HTTP_400_BAD_REQUEST,
                                     "error_text": serializer_list_non.errors[next(iter(serializer_list_non.errors))][0]},
                                    status=status.HTTP_400_BAD_REQUEST)

                serializer_list_non.save()
        return Response({"code":status.HTTP_200_OK}, status=status.HTTP_200_OK)


class GetForestlyBySubjectRFId(viewsets.ViewSet):
    def list(self, request, **kwargs):
        pk = kwargs.get('pk')
        lst = Forestly.objects.filter(id_subject_rf=pk)

        return JsonResponse({'data': GetForestlyBySubjectRFIdSerializer(lst, many=True).data}, safe=False)


class GetDistrictForestlyByForestlyId(viewsets.ViewSet):
    def list(self, request, **kwargs):
        pk = kwargs.get('pk')
        lst = DistrictForestly.objects.filter(id_forestly=pk)

        return JsonResponse({'data': GetDistrictForestlyByForestlyIdSerializer(lst, many=True).data}, safe=False)


class GetQuarterByDistrictId(viewsets.ViewSet):
    def list(self, request, **kwargs):
        pk = kwargs.get('pk')
        lst = Quarter.objects.filter(id_district_forestly=pk)

        return JsonResponse({'data': GetQuarterByDistrictForestlyIdSerializer(lst, many=True).data}, safe=False)




class UnionListRegions(generics.ListCreateAPIView):

    def post(self, request, **kwargs):
        for i in request.data['ids']:
            try:
                listing = ListRegion.objects.get(id = i['id'])
            except:
                continue
            lst = Sample.objects.filter(id_list_region = i['id']).update(id_list_region = request.data['id'])
            object_region = ListRegion.objects.get(id=i['id'])
            object_region.delete()
        print(request.data['id'])
        lst_sample = Sample.objects.filter(id_list_region = request.data['id'])
        return Response({"result": "Success"})


class UserRegistration(generics.ListCreateAPIView):

    def post(self, request, **kwargs):
        user_serializer = UserSerializer(data=request.data)
        serializers = ProfileSerializer(data=request.data)
        if not user_serializer.is_valid():
            # return Response({'error': status.HTTP_400_BAD_REQUEST},
            #          status=status.HTTP_400_BAD_REQUEST)
            return Response({"error": status.HTTP_400_BAD_REQUEST,
                             "error_text": user_serializer.errors[next(iter(user_serializer.errors))][0]},
                            status= status.HTTP_400_BAD_REQUEST)
        if not serializers.is_valid():
            # return Response({'error': status.HTTP_400_BAD_REQUEST},
            #          status=status.HTTP_400_BAD_REQUEST)
            return Response({"error": status.HTTP_400_BAD_REQUEST,
                             "error_text": serializers.errors[next(iter(serializers.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        user_serializer.save()
        serializers.save()
        # # user = Users.objects.get(id = user_serializer.data['id'])
        lst = Profile.objects.get(id = serializers.data['id'])
        lst.id_user_id = user_serializer.data['id']
        lst.save()

        return Response({"code": status.HTTP_201_CREATED}, status=status.HTTP_201_CREATED)



class UserAuth(generics.ListCreateAPIView):

    def post(self, request, **kwargs):
        # ser = UserSerializer(data=request.data)
        # ser.validate_email(request.data['email'])
        try:
            user = Users.objects.filter(email=request.data['email']).values(
                'id', "password", "email").get()
        except:
            return Response({ "error" :status.HTTP_401_UNAUTHORIZED}, status=status.HTTP_401_UNAUTHORIZED)
        if check_password(request.data["password"], user["password"]):
            profile = Profile.objects.filter(id_user_id = user['id']).values('id', 'FIO').get()
            return Response(profile)
        return Response({"error": status.HTTP_401_UNAUTHORIZED}, status=status.HTTP_401_UNAUTHORIZED)


class PhotoPointView(APIView):
    """
    Приемка фотокарточки

    """

    parser_classes = (MultiPartParser, FileUploadParser )

    # parser_classes = (FileUploadParser, MultiPartParser)

    def get(self, *args, **kwargs):
        if kwargs:
            lst = PhotoPoint.objects.filter(id_sample=kwargs['pk']).values("id", "photo", "date")
            # if len(lst) == 0: return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
            #                                   status=status.HTTP_404_NOT_FOUND)
            return Response(lst)
        return Response(PhotoPointSer(PhotoPoint.objects.all(), many=True).data)


    def post(self, request, format = None):
        serializer = PhotoPointSerializer(data=request.data, context=request)
        serializer.is_valid(raise_exception=True)
        serializer.save(id_sample_id = request.data.get('id_sample'),
                        photo = request.data.get('photo'),
                        longitude = request.data.get('longitude'),
                        latitude = request.data.get('latitude'),
                        date = request.data.get('date')
                        )
        return Response({"http": status.HTTP_200_OK}, status=200)


class AnroidDownland(APIView):

    def get(self, *args, **kwargs):
        return Response({"list": ListSerializer(List.objects.all(), many=True).data,
                         "listregion": ListRegionSerializer(ListRegion.objects.all(), many=True).data,
                         "sample": SampleSerializer(Sample.objects.all(), many=True).data,
                         "subjectRF": SubjectRFSerializer(SubjectRF.objects.all(), many=True).data,
                         "forestly": ForestlySerializer(Forestly.objects.all(), many=True).data,
                         "district_forestly": DistrictForestlySerializer(DistrictForestly.objects.all(), many=True).data,
                         "quarter": QuarterSerializer(Quarter.objects.all(), many=True).data,
                         "breeds": BreedSerializer(Breed.objects.all(), many=True).data,
                         "Undergrowth": UndergrowthSerializer(Undergrowth.objects.all(), many=True).data
                         })


class GetAllEqualListRegion(ListAPIView):
    queryset = ListRegion.objects.all()

    def get_queryset(self):
        return ListRegion.objects.all()

    def get(self, request, *args, **kwargs):
        self.queryset = self.get_queryset()
        local_queryset = list()
        id_list = list()
        for i in range(len(self.queryset)-1):
            if self.queryset[i].id in id_list:
                continue
            queryset = ListRegionSerializerId(
                ListRegion.objects.filter(date = self.queryset[i].date,
                                          sample_region = self.queryset[i].sample_region,
                                          id_quarter_id = self.queryset[i].id_quarter_id), many=True).data
            for j in range(len(queryset)):
                id_list.append(queryset[j].get('id'))
            if len(queryset) > 1:
                local_queryset.append(queryset)
        return Response({"query":local_queryset})


    def post(self, request, *args, **kwargs):
        self.queryset = self.get_queryset()
        local_queryset = list()
        id_list = list()
        date_request = request.data['date_in_days']
        for i in range(len(self.queryset) - 1):
            if self.queryset[i].id in id_list:
                continue
            queryset = ListRegionSerializerId(
                ListRegion.objects.filter(date=self.queryset[i].date,
                                          sample_region=self.queryset[i].sample_region,
                                          id_quarter_id=self.queryset[i].id_quarter_id), many=True).data
            for j in range(len(queryset)):
                id_list.append(queryset[j].get('id'))
            if len(queryset) > 1:
                local_queryset.append(queryset)
        return Response({"query": local_queryset})

# добавить soil_lot  в фильтры в описание участка и полевые карточки тоже
class ListRegionFilters(ListAPIView):
    """

    Фильтры перечетной ведомости участка

    """

    def post(self, request, *args, **kwargs):
        ser2 = ListRegion.objects.all()
        if request.data['bSubjectrf']:
            idSubjectrf = request.data['idSubjectrf']
            print("subjectrf")
            ser2 = ser2.filter(id_quarter__id_district_forestly__id_forestly__id_subject_rf = idSubjectrf)
        if request.data['bForestly']:
            idForestly = request.data['idForestly']
            ser2 = ser2.filter(id_quarter__id_district_forestly__id_forestly = idForestly)
        if request.data['bDistrictForestly']:
            idDistrictForestly = request.data['idDistrictForestly']
            ser2 = ser2.filter(id_quarter__id_district_forestly=idDistrictForestly)
        if request.data['bQuarter']:
            idQuarter = request.data['idQuarter']
            ser2 = ser2.filter(id_quarter = idQuarter)
        if request.data['bDate']:
            requestDate = request.data['date']
            ser2 = ser2.filter(date__gte = requestDate)
        if request.data['bDateSec']:
            requestDateSec = request.data['dateSec']
            ser2 = ser2.filter(date__lte = requestDateSec)
        if request.data['bSoil_lot']:
            requestSoil_lot = request.data['soil_lot']
            ser2 = ser2.filter(soil_lot = requestSoil_lot)
        ser2 = ListRegionSerializerId(ser2, many=True)
        return Response({"data":ser2.data})


class SendResponseSQLite(ListAPIView):
    '''
    Отправка БД sqlite
    '''
    def get(self, *args, **kwargs):
        import pathlib
        import testDjangosite.settings as settings
        # file_path = str(BASE_DIR) + "\\testDjangosite\\db.sqlite3"
        # file_path = settings.DATABASES['default']['NAME']
        file_path = str('..' / settings.BASE_DIR / 'db.sqlite3')
        response = Response({"BASED": file_path})
        return response


# , content_type="application/sqlite-x")
#         response['Content-Disposition'] = 'attachment; filename=db.db'

class GetSampleFromListRegionId(ListAPIView):

    def post(self, request, *args, **kwargs):
        ser = list(Sample.objects.filter(id_list_region = request.data['id']).values_list('id', flat=True))
        lst1 = SampleSerializerId(Sample.objects.all().filter(id__in = ser), many=True)
        return Response({"data": lst1.data})


class GetAllDescriptionRegion(ListAPIView):

    def get(self, *args, **kwargs):
        if kwargs:
            DescriptionRegionSerializer().validate_pk(kwargs['pk'])
            lst = DescriptionRegionSerializer(DescriptionRegion.objects.get(id=kwargs['pk'])).data
            try:
                lst_FieldCard = FieldCardSerializer(FieldCard.objects.get(id_list_region = lst['id_list_region'])).data
                lst.update({"id_field_card": lst_FieldCard['id']})
            except:
                lst.update({"id_field_card": ""})
            return Response({
                "DescriptionRegion":
                    lst})
        lst = DescriptionRegion.objects.all()
        return Response({"get": DescriptionRegionSerializer(lst, many=True).data})


    def put(self, request, *args, **kwargs):
        try:
            instance = DescriptionRegion.objects.get(pk=kwargs['pk'])
        except:
            return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                            status=status.HTTP_404_NOT_FOUND)
        try:
            instance_region = ListRegion.objects.get(id=request.data['id_list_region'])
        except:
            return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid list region id"},
                            status=status.HTTP_404_NOT_FOUND)
        ser_listregion = ListRegionUpdateNonMarkDel(data=request.data, instance=instance_region)
        ser_listregion.is_valid(raise_exception=True)
        ser_listregion.save()
        serealizer = DescriptionRegionSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({'code': status.HTTP_200_OK}, status=status.HTTP_200_OK)


class DescriptionRegionFilter(ListAPIView):
    '''
    Фильтр описания региона
    '''

    def post(self, request, *args, **kwargs):
        ser2 = DescriptionRegion.objects.all()
        if request.data['bSubjectrf']:
            idSubjectrf = request.data['idSubjectrf']
            print("subjectrf")
            ser2 = ser2.filter(id_list_region__id_quarter__id_district_forestly__id_forestly__id_subject_rf=idSubjectrf)
        if request.data['bForestly']:
            idForestly = request.data['idForestly']
            ser2 = ser2.filter(id_list_region__id_quarter__id_district_forestly__id_forestly=idForestly)
        if request.data['bDistrictForestly']:
            idDistrictForestly = request.data['idDistrictForestly']
            ser2 = ser2.filter(id_list_region__id_quarter__id_district_forestly=idDistrictForestly)
        if request.data['bQuarter']:
            idQuarter = request.data['idQuarter']
            ser2 = ser2.filter(id_list_region__id_quarter=idQuarter)
        if request.data['bDate']:
            # requestDate = request.data['date']
            ser2 = ser2.filter(id_list_region__date__gte=request.data['date'])
        if request.data['bDateSec']:
            requestDateSec = request.data['dateSec']
            ser2 = ser2.filter(id_list_region__date__lte=requestDateSec)
        if request.data['bSoil_lot']:
            requestSoil_lot = request.data['soil_lot']
            ser2 = ser2.filter(id_list_region__soil_lot = requestSoil_lot)
        ser2 = DescriptionRegionSerializer(ser2, many=True)
        return Response({"data": ser2.data})


class GetFieldCard(ListAPIView):

    '''
    GET all & get one
    '''

    def get(self, *args, **kwargs):
        if kwargs:
            FieldCardSerializer().validate_pk(kwargs['pk'])
            lst = FieldCardSerializer(FieldCard.objects.get(id=kwargs['pk'])).data
            try:
                lst_desc = DescriptionRegionSerializer(DescriptionRegion.objects.get(id_list_region=lst['id_list_region'])).data
                lst.update({"id_desc": lst_desc['id']})
            except:
                lst.update({"id_desc": ""})
            return Response({
                "FieldCard":
                    lst })
        lst = FieldCard.objects.all()
        return Response({"get": FieldCardSerializer(lst, many=True).data})


    def post(self, request, *args, **kwargs):
        list_serializer = ListRegionSerializer(data=request.data)
        if not list_serializer.is_valid():
            return Response({"error_list_region": status.HTTP_400_BAD_REQUEST,
                             "error_text": list_serializer.errors[next(iter(list_serializer.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        list_serializer.save()
        request.data.update({"id_list_region": list_serializer.data['id']})
        desc_serializer = DescriptionRegionSerializerNonEconomyAct(data=request.data)
        fieldcard_serializer = FieldCardSerializerNoneSapling(data=request.data)
        if not desc_serializer.is_valid():
            print(desc_serializer.errors)
            return Response({"error_desc": status.HTTP_400_BAD_REQUEST,
                             "error_text": desc_serializer.errors[next(iter(desc_serializer.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        if not fieldcard_serializer.is_valid():
            print(fieldcard_serializer.errors)
            return Response({"error_fieldcard": status.HTTP_400_BAD_REQUEST,
                             "error_text": fieldcard_serializer.errors[next(iter(fieldcard_serializer.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        desc_serializer.save()
        fieldcard_serializer.save()
        return Response({"id": fieldcard_serializer.data['id']}, status= status.HTTP_201_CREATED)


    def put(self, request, *args, **kwargs):
        try:
            instance = FieldCard.objects.get(pk=kwargs['pk'])
        except:
            return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                            status=status.HTTP_404_NOT_FOUND)
        try:
            instance_region = ListRegion.objects.get(id=request.data['id_list_region'])
        except:
            return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid list region id"},
                            status=status.HTTP_404_NOT_FOUND)
        ser_listregion = ListRegionUpdateNonMarkDel(data=request.data, instance=instance_region)
        if not ser_listregion.is_valid():
            return Response({"error": status.HTTP_400_BAD_REQUEST,
                             "error_text": ser_listregion.errors[next(iter(ser_listregion.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        ser_listregion.save()
        serealizer = FieldCardSerializer(data=request.data, instance=instance)
        if not serealizer.is_valid():
            return Response({"error": status.HTTP_400_BAD_REQUEST,
                             "error_text": serealizer.errors[next(iter(serealizer.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        serealizer.save()
        return Response({'code': status.HTTP_200_OK}, status=status.HTTP_200_OK)



class FieldCardFilter(ListAPIView):
    '''
    Фильтр для полевой карточки
    '''

    def post(self, request, *args, **kwargs):
        ser2 = FieldCard.objects.all()
        if request.data['bSubjectrf']:
            idSubjectrf = request.data['idSubjectrf']
            print("subjectrf")
            ser2 = ser2.filter(id_list_region__id_quarter__id_district_forestly__id_forestly__id_subject_rf=idSubjectrf)
        if request.data['bForestly']:
            idForestly = request.data['idForestly']
            ser2 = ser2.filter(id_list_region__id_quarter__id_district_forestly__id_forestly=idForestly)
        if request.data['bDistrictForestly']:
            idDistrictForestly = request.data['idDistrictForestly']
            ser2 = ser2.filter(id_list_region__id_quarter__id_district_forestly=idDistrictForestly)
        if request.data['bQuarter']:
            idQuarter = request.data['idQuarter']
            ser2 = ser2.filter(id_list_region__id_quarter=idQuarter)
        if request.data['bDate']:
            # requestDate = request.data['date']
            ser2 = ser2.filter(id_list_region__date__gte=request.data['date'])
        if request.data['bDateSec']:
            requestDateSec = request.data['dateSec']
            ser2 = ser2.filter(id_list_region__date__lte=requestDateSec)
        if request.data['bSoil_lot']:
            requestSoil_lot = request.data['soil_lot']
            ser2 = ser2.filter(id_list_region__soil_lot = requestSoil_lot)
        ser2 = FieldCardSerializer(ser2, many=True)
        return Response({"data": ser2.data})


class CreateListRegionByDescRegion(ListAPIView):

    def post(self, request, *args, **kwargs):
        list_serializer = ListRegionSerializer(data=request.data)
        if not list_serializer.is_valid():
            return Response({"error": status.HTTP_400_BAD_REQUEST,
                             "error_text": list_serializer.errors[next(iter(list_serializer.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        list_serializer.save()
        request.data.update({"id_list_region": list_serializer.data['id']})
        desc_serializer = DescriptionRegionSerializer(data=request.data)
        fieldcard_serializer = FieldCardSerializer(data=request.data)
        if not desc_serializer.is_valid():
            return Response({"error": status.HTTP_400_BAD_REQUEST,
                             "error_text": desc_serializer.errors[next(iter(desc_serializer.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        if not fieldcard_serializer.is_valid():
            return Response({"error": status.HTTP_400_BAD_REQUEST,
                             "error_text": fieldcard_serializer.errors[next(iter(fieldcard_serializer.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        desc_serializer.save()
        fieldcard_serializer.save()
        return Response({"code": status.HTTP_201_CREATED}, status=status.HTTP_201_CREATED)


class PlotCoeffViews(ListAPIView):

    def get(self, request, *args, **kwargs):
        if kwargs:
            try:
                lst = PlotCoeff.objects.get(id=kwargs['pk'])
                return Response({"get": PlotCoeffSerializer(lst).data}, status = status.HTTP_200_OK )
            except:
                return Response({"code": status.HTTP_400_BAD_REQUEST, "error_text": "invalid PlotCoeff id"},
                                status=status.HTTP_400_BAD_REQUEST)
        lst = PlotCoeff.objects.all()
        return Response({"get": PlotCoeffSerializer(lst, many=True).data}, status=status.HTTP_200_OK)

    def post(self, request):
        for i in request.data:
            if i['id'] != 0:
                self.put(i, pk = i['id'])
                continue
            serializer = PlotCoeffSerializer(data=i)
            if not serializer.is_valid():
                return Response({"error": status.HTTP_400_BAD_REQUEST,
                                 "error_text": serializer.errors[next(iter(serializer.errors))][0]},
                                status=status.HTTP_400_BAD_REQUEST)
            serializer.save()
        return Response({"code": status.HTTP_201_CREATED}, status=status.HTTP_201_CREATED)

    def put(self, request, **kwargs):
        instance = PlotCoeff.objects.get(id = kwargs['pk'])
        if type(request) is dict:
            serializer = PlotCoeffSerializer(data=request, instance=instance)
        else:
            serializer = PlotCoeffSerializer(data=request.data, instance=instance)
        if not serializer.is_valid():
            return Response({"error": status.HTTP_400_BAD_REQUEST,
                             "error_text": serializer.errors[next(iter(serializer.errors))][0]},
                            status=status.HTTP_400_BAD_REQUEST)
        serializer.save()
        return Response({"code": status.HTTP_200_OK }, status=status.HTTP_200_OK)


class PlotCoeffByFieldCardId(ListAPIView):

    def get(self, request, *args, **kwargs):
        try:
            lst = PlotCoeff.objects.filter(id_field_card = kwargs['id_field_card'])
            return Response({"get": PlotCoeffSerializer(lst, many=True).data}, status=status.HTTP_200_OK)
        except:
            return Response({"error": status.HTTP_404_NOT_FOUND, "error_text": "invalid FieldCard id"},
                            status=status.HTTP_404_NOT_FOUND)


class RatioCompositionCalculateInList(ListAPIView):


    def post(self, request, *args, **kwargs):
        from staticpy.calculate_ratio_composition import calculate_coeff
        return Response(calculate_coeff(request.data))


class FormingDocxView(ListAPIView):

    def post(self, request, *args, **kwargs):
        from staticpy import forming_docx

        path_docx = forming_docx.forming_docx_fieldcard(request.data)
        return Response({"document": path_docx})


class FormingDocxViewDescRegion(ListAPIView):

    def post(self, request, *args, **kwargs):
        from staticpy import forming_docx

        path_docx = forming_docx.forming_docx_desc_region(request.data)
        return Response({"document": path_docx})

# class testviews(ListAPIView):
#     print("BOOMBOX")
#     # file_patj = str()
# #     # print(BASE_DIR / 'test.xlsx' )
#     import pandas as pd
#     excel = pd.ExcelFile(rf"{BASE_DIR / 'Лесные районы РФ.xlsx'}")
#     sheetX = excel.parse(0)
#
#     print(sheetX)
#     # # sheetX = excel.parse()
#     for i in sheetX['Леса']:
#         print(1)
#         print(i)
#         ForestDistricts.objects.create(name_forest_district = i)


class ForestViewSet(viewsets.ModelViewSet):
    pass
