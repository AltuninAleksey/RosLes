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
            lst = Profile.objects.get(pk=kwargs['pk'])
            return Response({'get': ProfileSerializer(lst).data})
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
            lst = List.objects.get(pk=kwargs['pk'])
            return Response({'get': ListSerializer(lst).data})
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
            except:
                return Response({"Объект с данным id не найден"})
            serealizer = ListSerializer(data=request.data, instance=instance)
            serealizer.is_valid(raise_exception=True)
            serealizer.save()

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


class GpsView(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            lst = GPS.objects.get(pk=kwargs['pk'])
            return Response({'get': GPSSerializer(lst).data})
        lst = GPS.objects.all()
        return Response({'get': GPSSerializer(lst, many=True).data})

    def post(self, request):
        serializer = GPSSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
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


class ListRegionView(generics.ListCreateAPIView):
    model = ListRegion

    def get(self, request, *args, **kwargs):
        pk = kwargs.get('pk')
        if pk:
            lst = ListRegion.objects.filter(pk=pk)
            return JsonResponse(ListRegionSerializerId(lst, many=True).data, safe=False)
        lst = ListRegion.objects.all()
        serealizer_class = GetListRegionSerializer(lst, many=True)
        return Response({"get":ListRegionSerializer(lst, many=True).data})
        # lst = ListRegion.objects.all()
        # return JsonResponse(ListRegionSerializer(lst, many=True).data, safe=False)

    def post(self, request):
        serializer = ListRegionSerializer(data=request.data)

        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        if kwargs:
            try:
                instance = ListRegion.objects.get(pk=kwargs['pk'])
            except:
                return Response({"Объект с данным id не найден"})
            serealizer = ListRegionSerializer(data=request.data, instance=instance)
            serealizer.is_valid(raise_exception=True)
            serealizer.save()

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
                lst.mark_update = 0
                lst.save()
        return Response({"put": status.HTTP_200_OK})


class SampleView(generics.ListCreateAPIView):

    def get_serializer_class(self):
        return SampleSerializer

    
    def get(self, request, **kwargs):
        if kwargs:
            lst = Sample.objects.get(pk=kwargs['pk'])
            return Response({'get': SampleSerializer(lst).data})
        lst = Sample.objects.all()
        #         # return Response({'posts': list(lst) })
        return Response({'get': SampleSerializer(lst, many=True).data})

    def post(self, request):
        serializer = SampleSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        if kwargs:
            try:
                instance = Sample.objects.get(pk=kwargs['pk'])
            except:
                return Response({"Объект с данным id не найден"})
            serealizer = SampleSerializer(data=request.data, instance=instance)
            serealizer.is_valid(raise_exception=True)
            serealizer.save()

        for i in range(len(request.data['data'])):
            # try:
            #     instance = Sample.objects.get(id=request.data['data'][i]["id"])
            #     print(instance)
            # except:
            #     continue
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


class PostView(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            lst = Post.objects.get(pk=kwargs['pk'])
            return Response({'get': PostSerializer(lst).data})
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
            lst = WorkingBreeds.objects.get(pk=kwargs['pk'])
            return Response({'get': WorkingBreedsSerializer(lst).data})
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


class SubjectRFview(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            lst = SubjectRF.objects.get(pk=kwargs['pk'])
            return Response({'get': SubjectRFSerializer(lst).data})
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
            lst = Role.objects.get(pk=kwargs['pk'])
            return Response({'get': RoleSerializer(lst).data})
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
            lst = Reproduction.objects.get(pk=kwargs['pk'])
            return Response({'get': ReproductionSerializer(lst).data})
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
            lst = Forestly.objects.get(pk=kwargs['pk'])
            return Response({'get': ForestlySerializer(lst).data})
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
            return Response({"error": "Объект с данным id не найден"})

        serealizer = ForestlySerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class DistrictForestlyView(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            lst = DistrictForestly.objects.get(pk=kwargs['pk'])
            return Response({'get': DistrictForestlySerializer(lst).data})
        else:
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
            lst = Quarter.objects.get(pk=kwargs['pk'])
            return Response({'get': QuarterSerializer(lst).data})
        else:
            lst = Quarter.objects.all()
            return Response({'get':QuarterSerializer(lst, many=True).data})

    def post(self, request):
        serializer = QuarterSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
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
        return Response({"get":UndergrowthSerializer(Undergrowth.objects.all(), many=True).data})


class UndergrowthByDefaultView(APIView):

    def get(self, *args, **kwargs):
        return Response({"get":UndergrowthByDefaultSerializer(UndergrowthByDefault.objects.all(), many=True).data})


class BreedView(generics.ListCreateAPIView):
    def get(self, request, **kwargs):
        if kwargs:
            lst = Breed.objects.get(pk=kwargs['pk'])
            return Response({'get': BreedSerializer(lst).data})
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
            lst = Branches.objects.get(pk=kwargs['pk'])
            return Response({'get': BranchesSerializer(lst).data})
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
        lst = List.objects.filter(id_sample=kwargs.get('pk'))
        state = Sample.objects.filter(pk=pk)
        gps = GPS.objects.filter(id_sample=pk)

        return JsonResponse({'list_data': GetDocumentListSerializer(lst, many=True).data,
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


class CreateSampleAndOther(generics.ListCreateAPIView):
    def post(self, request, **kwargs):
        # serializer_list_region = ListRegionSerializer(data=request.data['sample'])
        # serializer_list_region.is_valid(raise_exception=True)
        # serializer_list_region.save()
        # request.data['sample'].update({'id_list_region': serializer_list_region.data['id']})
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
            # serializer_gps.save()
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
        user_serializer.is_valid(raise_exception=True)
        user_serializer.save()
        request.data.update({'id_user': user_serializer.data['id']})
        serializers = ProfileSerializer(data=request.data, context={'request':request})
        serializers.is_valid(raise_exception=True)
        serializers.save()
        return Response(status.HTTP_201_CREATED)


class UserAuth(generics.ListCreateAPIView):

    def post(self, request, **kwargs):
        # ser = UserSerializer(data=request.data)
        # ser.validate_email(request.data['email'])
        try:
            user = Users.objects.filter(email=request.data['email']).values(
                'id', "password", "email").get()
        except:
            return Response("invalid email")
        if check_password(request.data["password"], user["password"]):
            profile = Profile.objects.filter(id_user_id = user['id']).values('id', 'FIO').get()
            return Response(profile)
        return Response("invalid pass")


class PhotoPointView(APIView):
    """
    Приемка фотокарточки
    """

    parser_classes = (MultiPartParser, FileUploadParser )

    # parser_classes = (FileUploadParser, MultiPartParser)

    def get(self, *args, **kwargs):
        if kwargs:
            from testDjangosite.settings import MEDIA_ROOT
            import os
            # ser = PhotoPointSerializer(data={'id_sample': kwargs['pk']}).validate_sample(kwargs['pk'])
            lst = PhotoPoint.objects.filter(id_sample=kwargs['pk']).values("photo")
            if len(lst) == 0: return Response("id_sample1 not found")
            for i in range(len(lst)):
                file_path = os.path.join(MEDIA_ROOT, lst[i]['photo'])
                print(file_path)
                if os.path.isfile(file_path):
                    response = FileResponse(open(file_path, 'rb'))
                    response['Content-Disposition'] = 'attachment; filename=' + lst[i]['photo'].split('/')[-1]
                    response['X-Sendfile'] = file_path
                    return response
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


    # def post(self, request, format = None):
    #     serializer = PhotoPointSerializer(data=request.data)
    #     serializer.is_valid()
    #     serializer.save(id_sample_id = request.data.get('id_sample'),
    #                     photo = request.FILES.get('photo'))
    #     return Response(status=201)

    # parser_classes = (MultiPartParser, FormParser)
    # renderer_classes = [JSONRenderer]
    # parser_classes = (MultiPartParser, FormParser)

    # parser_classes = (MultiPartParser, FormParser)
    # parser_classes = (FileUploadParser, MultiPartParser)
    # def post(self, request, format = 'jpg', *args, **kwargs):
    #
    #     file_serializer = PhotoPointSerializer(data=request.data)
    #     if file_serializer.is_valid():
    #         file_serializer.save(id_sample_id = 3,
    #                              photo = request.FILES.get('file'))
    #         print(file_serializer)
    #     return Response("vse ok")

    # def post(self, request, *args, **kwargs):
    #     uploads_serializer = PhotoPointSerializer(data=request.data)
    #
    #     if uploads_serializer.is_valid():
    #         uploads_serializer.save()
    #         return Response(uploads_serializer.data, status=status.HTTP_201_CREATED)
    #     else:
    #         return Response(uploads_serializer.errors, status=status.HTTP_400_BAD_REQUEST)
    # parser_classes = (FileUploadParser, )
    # # parser_classes = (MultiPartParser, FormParser)
    # def post(self, request, filename):
    #     print(request.data)
    #     print(request.FILES)
    #     print(request.data)
    #     serializer = PhotoPointSerializer(data=request.data)
    #     serializer.is_valid()
    #     serializer.save(id_sample_id = 3,
    #                     photo = request.FILES.get('file'))
    #     return Response("kaif")

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
            return Response({
                "DescriptionRegion":
                    lst,
                "ListRegion": ListRegionSerializerId(ListRegion.objects.get(id=lst['id_list_region'])).data
            })
        lst = DescriptionRegion.objects.all()
        return Response({"get": DescriptionRegionSerializer(lst, many=True).data})


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
            lst = FieldCardSerializer(FieldСard.objects.get(id=kwargs['pk'])).data
            return Response({
                "FieldCard":
                    lst,
                "ListRegion": ListRegionSerializerId(ListRegion.objects.get(id=lst['id_list_region'])).data
            })
        lst = FieldСard.objects.all()
        return Response({"get": FieldCardSerializer(lst, many=True).data})


class FieldCardFilter(ListAPIView):
    '''
    Фильтр для полевой карточки
    '''

    def post(self, request, *args, **kwargs):
        ser2 = FieldСard.objects.all()
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


# class SchemaMixingBreedsView(ListAPIView):
#
#     def post(self, request, *args, **kwargs):
#         print("111")
#         SchemaMixingBreeds.objects.update_or_create(name_schema=request.data['name'])
#         print("222")
#         return Response("k")

class ForestViewSet(viewsets.ModelViewSet):
    pass
