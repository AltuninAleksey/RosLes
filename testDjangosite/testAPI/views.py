from django.shortcuts import render
from rest_framework import generics
from rest_framework.response import Response
from rest_framework.decorators import api_view
from .models import Table
from testAPI.serializers import TableSerializer
# Create your views here.

class testAPIView(generics.ListAPIView):

    def get(self, request):
        lst = Table.objects.all().values()
        # return Response({'posts': list(lst) })
        return Response({'name': TableSerializer(lst, many=True).data})

    # def post(self, request):
    #     serializer = TableSerializer(data=request.data)
    #     serializer.is_valid(raise_exception=True)
    #     post_new = Table.objects.create(
    #         name = request.data['name'],
    #         age = request.data['age']
    #     )
    #     return Response({'post': TableSerializer(post_new).data})



def index(request):
    return render(request, "testDjangosite/index.html")
