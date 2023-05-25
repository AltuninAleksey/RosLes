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
        serializer.is_valid(raise_exception=True)
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

class ListRegionViewUpdate(ListView):

    def put(self, request, *args, **kwargs):
        try:
            instance = ListRegion.objects.get(pk=kwargs['pk'])
        except:
            return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                            status=status.HTTP_404_NOT_FOUND)

        serealizer = ListRegionSerializer(data=request.data, instance=instance)
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
            print(123)
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
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        try:
            instance = SubjectRF.objects.get(pk=kwargs['pk'])
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = SubjectRFSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


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
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        try:
            instance = Forestly.objects.get(pk=kwargs['pk'])
        except:
            return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                            status=status.HTTP_404_NOT_FOUND)
        serealizer = ForestlySerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


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
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        try:
            instance = DistrictForestly.objects.get(pk=kwargs['pk'])
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = DistrictForestlySerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
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
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        try:
            instance = Quarter.objects.get(pk=kwargs['id'])
        except:
            return Response({"error": "Объект с данным id не найден"})
        serealizer = QuarterSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


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
            try:
                lst = List.objects.filter(id_sample__id_list_region=kwargs["pk"]).values(
                    "age", "ratio_composition", "avg_height", "avg_diameter", "count_of_plants", "id_breed")
                # print(lst)
                return Response(lst)
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


class CreateSampleAndOther(ListAPIView):

    def post(self, request, **kwargs):
        # serializer_list_region = ListRegionSerializer(data=request.data['sample'])
        # serializer_list_region.is_valid(raise_exception=True)
        # serializer_list_region.save()
        # request.data['sample'].update({'id_list_region': serializer_list_region.data['id']})
        # serializer_undergrowth = UndergrowthSerializer()
        # serializer_photopoint = PhotoPointSerializer()
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
        # if len(request.data['photopoint']) != 0:
        #     pass
        # if len(request.data['undergrowth']) != 0:
        #     # for k in range(len(request.data['undergrowth'])):
        #     serializer_undergrowth = UndergrowthSerializer(data=request.data['undergrowth'], many=True)
        #     # serializer_gps.save()
        # serializer_list_region.is_valid(raise_exception=True)
        # serializer_list_region.save()
        return Response({'sample': serializer.data,
                         # 'list_region': serializer_list_region.data,
                         "list": serializer_list.data,
                         "gps": serializer_gps.data})

    def put(self, request, *args, **kwargs):
        i = 0
        j = 0
        try:
            instance = Sample.objects.get(pk=request.data['sample']['id'])
        except:
            return Response({"error": "Объект с данным id не найден"})
        serializer = SampleSerializer(data=request.data['sample'], instance=instance)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        if len(request.data['list_data']) != 0:
            while i < len(request.data['list_data']):
                try:
                    instance_list = List.objects.get(pk=request.data['list_data'][i]['id'])
                    serializer_list = ListSerializer(data=request.data['list_data'][i], instance=instance_list)
                except:
                    serializer_list = ListSerializer(data=request.data['list_data'][i])
                # serializer_list = ListSerializer(data=request.data['list_data'][i], instance=instance_list)
                serializer_list.is_valid(raise_exception=True)
                serializer_list.save()
                i += 1
        else:
            serializer_list = ListSerializer(data="")
            serializer_list.is_valid(raise_exception=False)
        if len(request.data['gps_data']) != 0:
            while j < len(request.data['gps_data']):
                try:
                    instance_gps = GPS.objects.get(pk=request.data['gps_data'][j]['id'])
                    serializer_gps = GPSSerializer(data=request.data['gps_data'][j], instance=instance_gps)
                except:
                    serializer_gps = GPSSerializer(data=request.data['gps_data'][j])
                serializer_gps.is_valid(raise_exception=True)
                serializer_gps.save()
                j += 1
        else:
            serializer_gps = GPSSerializer(data="")
            serializer_gps.is_valid(raise_exception=False)

        return Response({"sample": serializer.data,
                         # "list_region": serializer_list_region.data,
                         "list": serializer_list.data,
                         "gps": serializer_gps.data})


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
            if len(lst) == 0: return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                                              status=status.HTTP_404_NOT_FOUND)
            return Response(lst)
            from testDjangosite.settings import MEDIA_ROOT
            import os
            # lst = PhotoPoint.objects.filter(pk=kwargs['pk']).values("photo")
            # if len(lst) == 0:
            #     return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
            #                     status=status.HTTP_404_NOT_FOUND)
            # return Response(lst)
            # file_path = os.path.join(MEDIA_ROOT, lst['photo'])
            # print(file_path)
            # if os.path.isfile(file_path):
            # response = FileResponse(open(file_path, 'rb'))
            # response['Content-Disposition'] = 'attachment; filename=' + lst['photo'].split('/')[-1]
            # response['X-Sendfile'] = file_path
            # return response
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

    def put(self, request, *args, **kwargs):
        try:
            instance = FieldCard.objects.get(pk=kwargs['pk'])

        except:
            return Response({'error': status.HTTP_404_NOT_FOUND, 'error_text': "invalid id"},
                            status=status.HTTP_404_NOT_FOUND)

        serealizer = FieldCardSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
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
        ser2 = FieldCardSerializer(ser2, many=True)
        return Response({"data": ser2.data})


# class testviews(ListAPIView):
#     print("BOOMBOX")
#     # file_patj = str()
# #     # print(BASE_DIR / 'test.xlsx' )
#     import pandas as pd
#     excel = pd.ExcelFile(rf"{BASE_DIR / 'test.xlsx'}")
#     sheetX = excel.parse(0)
#     print(sheetX)
#     column1 = sheetX['Порода'][3]
#     # print(sheetX['Порода'][3])
#     # print(sheetX['Порода'])
#     ser = WorkingBreedsSerializer
    # for i in sheetX:
    #     print(sheetX['Порода'][i])
    #     print('====')
#         WorkingBreeds.objects.create(name_breeds = i)




class ForestViewSet(viewsets.ModelViewSet):
    pass
