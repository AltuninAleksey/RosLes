import json

import simplejson
from django.http import JsonResponse, HttpResponse
from rest_framework import generics
from rest_framework.response import Response
from rest_framework import viewsets, permissions
from rest_framework.decorators import api_view
from .models import *
from djangoForest.serializers import *
from collections import namedtuple


class TestAPIView(generics.ListAPIView):
    def get(self, request):
        lst = Profile.objects.all()
        return Response({'': ProfileSerializer(lst, many=True).data})


class ProfileView(generics.ListCreateAPIView):
    def get(self, request):
        lst = Profile.objects.all()
        return Response({'get': ProfileSerializer(lst, many=True).data})

    def post(self, request):
        serializer = ProfileSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        pk = kwargs.get("pk")
        print(pk)
        if not pk:
            return Response({"error": "Method PUT in Subjects RF not allowed"})

        try:
            instance = Profile.objects.get(pk=pk)
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = ProfileSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class ListView(generics.ListCreateAPIView):
    def get(self, request):
        lst = List.objects.all()
        print("1")
        print(Response({'post': ListSerializer(lst, many=True).data}))
        return Response({'post': ListSerializer(lst, many=True).data})
        # return render(request, 'listview/list.html', {
        #     "post": lst
        # })

    def post(self, request):
        serializer = ListSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        pk = kwargs.get("pk")
        print(pk)
        if not pk:
            return Response({"error": "Method PUT in Subjects RF not allowed"})

        try:
            instance = List.objects.get(pk=pk)
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = ListSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class GpsView(generics.ListCreateAPIView):
    def get(self, request):
        lst = GPS.objects.all()
        return Response({'get': GPSSerializer(lst, many=True).data})

    def post(self, request):
        serializer = GPSSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        pk = kwargs.get("pk")
        print(pk)
        if not pk:
            return Response({"error": "Method PUT in Subjects RF not allowed"})

        try:
            instance = GPS.objects.get(pk=pk)
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = GPSSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


# class RegionView(generics.ListCreateAPIView):
#     def get(self, request):
#         lst = Region.objects.all()
#         return Response({'get': RegionSerializer(lst, many=True).data})
#
#     def post(self, request):
#         serializer = RegionSerializer(data=request.data)
#         serializer.is_valid(raise_exception=True)
#         serializer.save()
#         return Response({'post': serializer.data})
#
#     def put(self, request, *args, **kwargs):
#         pk = kwargs.get("pk")
#         print(pk)
#         if not pk:
#             return Response({"error": "Method PUT in Subjects RF not allowed"})
#
#         try:
#             instance = Region.objects.get(pk=pk)
#         except:
#             return Response({"error": "Объект с данным id не найден"})
#
#         serealizer = RegionSerializer(data=request.data, instance=instance)
#         serealizer.is_valid(raise_exception=True)
#         serealizer.save()
#         return Response({"put": serealizer.data})


class ListRegionView(generics.ListCreateAPIView):
    model = ListRegion

    def get(self, request, *args, **kwargs):
        print(kwargs.get('pk'))
        pk = kwargs.get('pk')
        if pk:
            lst = ListRegion.objects.filter(pk=pk)
            return JsonResponse(ListRegionSerializerId(lst, many=True).data, safe=False)
        else:
            lst = ListRegion.objects.all()
            serealizer_class = ListRegionSerializer(lst, many=True)
            return JsonResponse(ListRegionSerializerId(lst, many=True).data, safe=False)
        # lst = ListRegion.objects.all()
        # return JsonResponse(ListRegionSerializer(lst, many=True).data, safe=False)

    def post(self, request):
        serializer = ListRegionSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        pk = kwargs.get("pk")
        print(pk)
        if not pk:
            return Response({"error": "Method PUT in Subjects RF not allowed"})

        try:
            instance = ListRegion.objects.get(pk=pk)
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = ListRegionSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class SampleView(generics.ListCreateAPIView):
    def get(self, request):
        lst = Sample.objects.all()
        #         # return Response({'posts': list(lst) })
        return Response({'get': SampleSerializer(lst, many=True).data})

    def post(self, request):
        serializer = SampleSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        pk = kwargs.get("pk")
        print(pk)
        if not pk:
            return Response({"error": "Method PUT not allowed"})
        try:
            instance = Sample.objects.get(pk=pk)
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = SampleSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class PostView(generics.ListCreateAPIView):
    def get(self, request):
        lst = Post.objects.all()
        return Response({'get': PostSerializer(lst, many=True).data})

    def post(self, request):
        serializer = PostSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        pk = kwargs.get("pk")
        print(pk)
        if not pk:
            return Response({"error": "Method PUT in Subjects RF not allowed"})

        try:
            instance = Post.objects.get(pk=pk)
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = PostSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class WorkingBreedView(generics.ListCreateAPIView):
    def get(self, request):
        lst = WorkingBreeds.objects.all()
        return Response({'get': WorkingBreedsSerializer(lst, many=True).data})

    def post(self, request):
        serializer = WorkingBreedsSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        pk = kwargs.get("pk")
        print(pk)
        if not pk:
            return Response({"error": "Method PUT in Subjects RF not allowed"})

        try:
            instance = WorkingBreeds.objects.get(pk=pk)
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = WorkingBreedsSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class SubjectRFview(generics.ListCreateAPIView):
    def get(self, request):
        lst = SubjectRF.objects.all()
        return Response({'get': SubjectRFSerializer(lst, many=True).data})

    def post(self, request):
        serializer = SubjectRFSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        pk = kwargs.get("pk")
        print(pk)
        if not pk:
            return Response({"error": "Method PUT in Subjects RF not allowed"})

        try:
            instance = SubjectRF.objects.get(pk=pk)
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = SubjectRFSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class RoleView(generics.ListCreateAPIView):
    def get(self, request):
        lst = Role.objects.all()
        return Response({'get': RoleSerializer(lst, many=True).data})

    def post(self, request):
        serializer = RoleSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        pk = kwargs.get("pk")
        print(pk)
        if not pk:
            return Response({"error": "Method PUT not allowed"})

        try:
            instance = Role.objects.get(pk=pk)
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = RoleSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class ReproductionView(generics.ListCreateAPIView):
    def get(self, request):
        lst = Reproduction.objects.all()
        return Response({'get': ReproductionSerializer(lst, many=True).data})

    def post(self, request):
        serializer = ReproductionSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        pk = kwargs.get("pk")
        print(pk)
        if not pk:
            return Response({"error": "Method PUT not allowed"})

        try:
            instance = Reproduction.objects.get(pk=pk)
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = ReproductionSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class ForestlyView(generics.ListCreateAPIView):
    def get(self, request):
        lst = Forestly.objects.all()
        return Response({'get': ForestlySerializer(lst, many=True).data})

    def post(self, request):
        serializer = ForestlySerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        pk = kwargs.get("pk")
        print(pk)
        if not pk:
            return Response({"error": "Method PUT not allowed"})

        try:
            instance = Forestly.objects.get(pk=pk)
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = ForestlySerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class DistrictForestlyView(generics.ListCreateAPIView):
    def get(self, request):
        lst = DistrictForestly.objects.all()
        return Response({'get': DistrictForestlySerializer(lst, many=True).data})

    def post(self, request):
        serializer = DistrictForestlySerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        pk = kwargs.get("pk")
        print(pk)
        if not pk:
            return Response({"error": "Method PUT not allowed"})

        try:
            instance = DistrictForestly.objects.get(pk=pk)
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = DistrictForestlySerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class QuarterView(generics.ListCreateAPIView):
    def get(self, request):
        lst = Quarter.objects.all()
        return Response({'get':QuarterSerializer(lst, many=True).data})

    def post(self, request):
        serializer = QuarterSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        pk = kwargs.get("pk")
        if not pk:
            return Response({"error": "Method put not allowed"})

        try:
            instance = Breed.objects.get(pk=pk)
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = QuarterSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class BreedView(generics.ListCreateAPIView):
    def get(self, request):
        lst = Breed.objects.all()
        return Response({'get': BreedSerializer(lst, many=True).data})

    def post(self, request):
        serializer = BreedSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        pk = kwargs.get("pk")
        print(pk)
        if not pk:
            return Response({"error": "Method PUT not allowed"})

        try:
            instance = Breed.objects.get(pk=pk)
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = BreedSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class BranchesView(generics.ListCreateAPIView):
    def get(self, request):
        lst = Branches.objects.all()
        return Response({'get': BranchesSerializer(lst, many=True).data})

    def post(self, request):
        serializer = BranchesSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        pk = kwargs.get("pk")
        print(pk)
        if not pk:
            return Response({"error": "Method PUT not allowed"})

        try:
            instance = Branches.objects.get(pk=pk)
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = BranchesSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class AllForestlyViewSet(viewsets.ViewSet):
    def list(self, request):
        AllForest = namedtuple('Forestly', ('district_forestly', 'forestly', 'subjectrf'))
        lst = AllForest(
            district_forestly=DistrictForestly.objects.all(),
            forestly=Forestly.objects.all(),
            subjectrf=SubjectRF.objects.all(),
        )
        serializer = AllForestSerializer(lst)
        return Response(serializer.data)


class GetDocumentListData(viewsets.ViewSet):
    def list(self, request, *args, **kwargs):
        pk = kwargs.get('pk')
        print(pk)
        lst = List.objects.filter(id_sample=pk)
        state = Sample.objects.filter(pk=pk)
        gps = GPS.objects.filter(id_sample=pk)
        return JsonResponse({'list-data': GetDocumentListSerializer(lst, many=True).data,
                             'post-data': GetFromSampleProfileSerializer(state, many=True).data,
                             'gps-data': GetGPS(gps, many=True).data}, safe=False)


class ForestViewSet(viewsets.ModelViewSet):
    pass
