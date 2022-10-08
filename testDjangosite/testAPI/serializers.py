import io

from rest_framework import serializers
from rest_framework.renderers import JSONRenderer
from rest_framework.parsers import JSONParser
from .models import Table


#
# class testAPImodel:
#     def __init__(self, name, age):
#         self.name = name
#         self.age = age


class TableSerializer(serializers.Serializer):
    name = serializers.CharField(max_length=255)
    age = serializers.IntegerField()


# def encode():
#     model = testAPImodel('Дмитрий Цыгановский', '20')
#     model_sr = TableSerializer(model)
#     json = JSONRenderer().render(model_sr.data)
#     print(json)
#
#
# def decode():
#     stream = io.BytesIO()
#     data = JSONParser().parse(stream)
#     serializer = TableSerializer(data=data)
#     serializer.is_valid()
#     print(serializer.validated_data)


