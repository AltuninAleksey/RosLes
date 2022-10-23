from django.shortcuts import render
from rest_framework import generics
from rest_framework.response import Response
from rest_framework import viewsets, permissions
from rest_framework.decorators import api_view
from .models import *
from djangoForest.serializers import *


class TestAPIView(generics.ListAPIView):
    def get(self, request):
        lst = Profile.objects.all().values()
        return Response({'': ProfileSerializer(lst, many=True).data})


class ProfileView(generics.ListCreateAPIView):
    def get(self, request):
        lst = Profile.objects.all().values()
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
        lst = Profile.objects.all().values()
        return Response({'get': ListSerializer(lst, many=True).data})

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
        lst = GPS.objects.all().values()
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


class RegionView(generics.ListCreateAPIView):
    def get(self, request):
        lst = Region.objects.all().values()
        return Response({'get': RegionSerializer(lst, many=True).data})

    def post(self, request):
        serializer = RegionSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({'post': serializer.data})

    def put(self, request, *args, **kwargs):
        pk = kwargs.get("pk")
        print(pk)
        if not pk:
            return Response({"error": "Method PUT in Subjects RF not allowed"})

        try:
            instance = Region.objects.get(pk=pk)
        except:
            return Response({"error": "Объект с данным id не найден"})

        serealizer = RegionSerializer(data=request.data, instance=instance)
        serealizer.is_valid(raise_exception=True)
        serealizer.save()
        return Response({"put": serealizer.data})


class ListRegionView(generics.ListCreateAPIView):
    def get(self, request):
        lst = ListRegion.objects.all().values()
        return Response({'get': ListRegionSerializer(lst, many=True).data})

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
        lst = Sample.objects.all().values()
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
        lst = Post.objects.all().values()
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
        lst = WorkingBreeds.objects.all().values()
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
        lst = SubjectRF.objects.all().values()
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
        lst = Role.objects.all().values()
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
        lst = Reproduction.objects.all().values()
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
        lst = Forestly.objects.all().values()
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
        lst = DistrictForestly.objects.all().values()
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


class BreedView(generics.ListCreateAPIView):
    def get(self, request):
        lst = Breed.objects.all().values()
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
        lst = Branches.objects.all().values()
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


class ForestViewSet(viewsets.ModelViewSet):
    pass
