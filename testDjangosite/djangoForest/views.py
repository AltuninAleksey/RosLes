import json

import simplejson
from django.http import JsonResponse, HttpResponse
from rest_framework import generics
from rest_framework.response import Response
from rest_framework import viewsets, permissions
from rest_framework.authtoken.models import Token
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
        try:
            instance = List.objects.get(pk=kwargs['pk'])
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = ListSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


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
            return JsonResponse(ListRegionSerializer(lst, many=True).data, safe=False)
        else:
            lst = ListRegion.objects.all()
            serealizer_class = GetListRegionSerializer(lst, many=True)
            return JsonResponse(ListRegionSerializer(lst, many=True).data, safe=False)
        # lst = ListRegion.objects.all()
        # return JsonResponse(ListRegionSerializer(lst, many=True).data, safe=False)

    def post(self, request):
        serializer = ListRegionSerializer(data=request.data)

        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        try:
            instance = ListRegion.objects.get(pk=kwargs['pk'])
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = ListRegionSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class SampleView(generics.ListCreateAPIView):
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
        try:
            instance = Sample.objects.get(pk=kwargs['pk'])
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = SampleSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


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

    def get(self, request, **kwargs):
        try:
            lst = Sample.objects.filter(id_list_region = request.data['id2']).update(id_list_region = request.data['id1'])
            object_region = ListRegion.objects.get(id=request.data['id2'])
            object_region.delete()
            return JsonResponse({'result': 'success'})
        except:
            return JsonResponse({'result': 'error'})


class UserRegistration(generics.ListCreateAPIView):

    def post(self, request, **kwargs):
        serializers = ProfileSerializer(data=request.data, context={'request':request})
        serializers.is_valid(raise_exception=True)
        serializers.save()
        user = serializers.data['id']
        token, created = Token.objects.get_or_create(user_id=user)
        return Response({
            'token': token.key,
        })


class UserAuth(generics.ListCreateAPIView):

    def get(self, request, **kwargs):
        user = Users.objects.filter(email = request.data['email'], password = request.data['password']).values('id').get()
        token = Token.objects.get(user_id = user['id'])
        return Response({
            'token': token.key
        })

class ForestViewSet(viewsets.ModelViewSet):
    pass
