import io

from django.contrib.auth.hashers import make_password
from rest_framework import serializers
from .models import *


class TableSerializer(serializers.ModelSerializer):
    class Meta:
        model = Table
        fields = '__all__'


class ProfileSerializer(serializers.ModelSerializer):
    class Meta:
        model = Profile
        fields = '__all__'

    def create(self, validated_data):
        return Profile.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.FIO = validated_data.get("FIO")
        instance.phoneNumber = validated_data.get("phoneNumber")
        instance.email = validated_data.get("email")
        instance.id_post = validated_data.get("id_post")
        instance.id_working_breeds = validated_data.get("id_working_breeds")
        instance.id_role = validated_data.get("id_role")
        instance.id_branches = validated_data.get("id_branches")
        instance.save()
        return instance


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = Users
        fields = '__all__'
        extra_kwargs= {"email": {"error_messages": {"required": {"Это обязательное поле"}}}}

    def create(self, validated_data):
        return Users.objects.create(email = validated_data['email'],
                                    password = make_password(validated_data['password'], "pbkdf2_sha256")
                                    )

    def validate_email(self, value):
        try:
            Users.objects.filter(email=value).values(
                'id', "password", "email").get()
            return value
        except:
            raise serializers.ValidationError("invalid email")
class ListSerializer(serializers.ModelSerializer):
    class Meta:
        model = List
        fields = '__all__'

    def create(self, validated_data):
        return List.objects.create(**validated_data)

    def update(self, instance, validated_data):

        instance.id_sample = validated_data.get("id_sample")
        instance.id_breed = validated_data.get("id_breed")
        instance.id_type_of_reproduction = validated_data.get("id_type_of_reproduction")
        instance.id_undergrowth = validated_data.get("id_undergrowth")
        instance.to0_2 = validated_data.get("to0_2")
        instance.from0_21To0_5 = validated_data.get("from0_21To0_5")
        instance.from0_6To1_0 = validated_data.get("from0_6To1_0")
        instance.from1_1to1_5 = validated_data.get("from1_1to1_5")
        instance.from1_5 = validated_data.get("from1_5")
        instance.max_height = validated_data.get("max_height")
        instance.avg_diameter = validated_data.get("avg_diameter")
        instance.count_of_plants = validated_data.get("count_of_plants")
        instance.avg_height = validated_data.get("avg_height")
        instance.avg_height_undergrowth = validated_data.get("avg_height_undergrowth")
        instance.main = validated_data.get("main")
        instance.mark_update = 0
        instance.save()
        return instance


class GPSSerializer(serializers.ModelSerializer):
    class Meta:
        model = GPS
        fields = '__all__'

    def create(self, validated_data):
        return GPS.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.id_sample = validated_data.get("id_sample")
        instance.latitude = validated_data.get("latitude")
        instance.longitude = validated_data.get("longitude")
        instance.flag_center = validated_data.get("flag_center")
        instance.save()
        return instance

#
# class RegionSerializer(serializers.ModelSerializer):
#     class Meta:
#         model = Region
#         fields = '__all__'
#
#     def create(self, validated_data):
#         return Region.objects.create(**validated_data)
#
#     def update(self, instance, validated_data):
#         instance.id_subject_rf = validated_data.get("id_subject_rf", instance.id_subject_rf)
#         instance.id_forestly = validated_data.get("id_forestly", instance.id_forestly)
#         instance.id_district_forestly = validated_data.get("id_district_forestly", instance.id_district_forestly)
#         instance.quarter = validated_data.get("quarter", instance.quarter)
#         instance.soil_lot = validated_data.get("soil_lot", instance.soil_lot)
#         instance.sample_region = validated_data.get("sample_region", instance.sample_region)
#         instance.save()
#         return instance


class SampleSerializer(serializers.ModelSerializer):
    # number_region = serializers.CharField(source='id_list_region.number_region')


    class Meta:
        model = Sample
        fields = '__all__'

    def create(self, validated_data):
        return Sample.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.id_list_region = validated_data.get("id_list_region")
        instance.date = validated_data.get("date")
        instance.sample_area = validated_data.get("sample_area")
        instance.id_profile = validated_data.get("id_profile")
        instance.soil_lot = validated_data.get("soil_lot")
        instance.id_quarter = validated_data.get("id_quarter")
        instance.id_list_region = validated_data.get("id_list_region")
        instance.width = validated_data.get("width")
        instance.lenght = validated_data.get("lenght")
        instance.square = validated_data.get("square")
        instance.mark_update = 0
        instance.save()
        return instance


class SampleSerializerId(serializers.Serializer):
    id = serializers.IntegerField()
    date = serializers.DateField()
    sample_area = serializers.FloatField()
    id_profile = serializers.CharField()
    id_quarter = serializers.CharField()
    id_district_forestly = serializers.CharField(source='id_quarter.id_district_forestly.id')
    id_forestly = serializers.CharField(source='id_quarter.id_district_forestly.id_forestly.id')
    id_subject_rf = serializers.CharField(source='id_quarter.id_district_forestly.id_forestly.id_subject_rf.id')
    width = serializers.FloatField()
    lenght = serializers.FloatField()
    square = serializers.FloatField()
    soil_lot = serializers.CharField()
    number_region = serializers.CharField(max_length=100, source='id_list_region.number_region')



class PostSerializer(serializers.ModelSerializer):
    class Meta:
        model = Post
        fields = '__all__'

    def create(self, validated_data):
        return Post.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.post_name = validated_data.get("post_name")
        instance.save()
        return instance


class WorkingBreedsSerializer(serializers.ModelSerializer):
    class Meta:
        model = WorkingBreeds
        fields = '__all__'

    def create(self, validated_data):
        return WorkingBreeds.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.name_breeds = validated_data.get("name_breeds")
        instance.save()
        return instance


class SubjectRFSerializer(serializers.ModelSerializer):
    class Meta:
        model = SubjectRF
        fields = '__all__'

    def create(self, validated_data):
        return SubjectRF.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.name_subject_RF = validated_data.get("name_subject_RF")
        instance.save()
        return instance


class RoleSerializer(serializers.ModelSerializer):
    class Meta:
        model = Role
        fields = '__all__'

    def create(self, validated_data):
        return Role.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.name_role = validated_data.get("name_role")
        instance.save()
        return instance


class ReproductionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Reproduction
        fields = '__all__'

    def create(self, validated_data):
        return Reproduction.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.name_reproduction = validated_data.get("name_reproduction")
        instance.save()
        return instance


class ForestlySerializer(serializers.ModelSerializer):
    class Meta:
        model = Forestly
        fields = '__all__'

    def create(self, validated_data):
        return Forestly.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.name_forestly = validated_data.get("name_forestly")
        instance.id_subject_rf = validated_data.get("id_subjecr_rf")
        instance.save()
        return instance


class DistrictForestlySerializer(serializers.ModelSerializer):
    class Meta:
        model = DistrictForestly
        fields = '__all__'

    def create(self, validated_data):
        return DistrictForestly.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.name_district_forestly = validated_data.get("name_district_forestly")
        instance.id_forestly = validated_data.get("id_forestly")
        instance.save()
        return instance


class BreedSerializer(serializers.ModelSerializer):
    class Meta:
        model = Breed
        fields = '__all__'

    def create(self, validated_data):
        return Breed.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.name_breed = validated_data.get("name_breed")
        instance.save()
        return instance

class UndergrowthSerializer(serializers.ModelSerializer):

    class Meta:
        model = Undergrowth
        fields = '__all__'

    def create(self, validated_data):
        return Undergrowth.objects.create(**validated_data)


class UndergrowthByDefaultSerializer(serializers.ModelSerializer):

    class Meta:
        model = UndergrowthByDefault
        fields = '__all__'

    def create(self, validated_data):
        return UndergrowthByDefault.objects.create(**validated_data)

class QuarterSerializer(serializers.ModelSerializer):
    class Meta:
        model = Quarter
        fields = '__all__'

    def create(self, validated_data):
        return Quarter.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.quarter_name = validated_data.get('quarter_name')
        instance.id_district_forestly = validated_data.get('id_district_forestly')
        instance.save()
        return instance


class BranchesSerializer(serializers.ModelSerializer):
    class Meta:
        model = Branches
        fields = '__all__'

    def create(self, validated_data):
        return Branches.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.name_branch = validated_data.get("name_branch")
        instance.save()
        return instance


class GetListRegionSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    date = serializers.DateField()
    sample_region = serializers.CharField(max_length=300)
    mark_del = serializers.BooleanField()
    mark_update = serializers.BooleanField()
    id_quarter = serializers.CharField()
    quarter = serializers.CharField(source='id_quarter.quarter_name')
    district_forestly = serializers.CharField(source='id_quarter.id_district_forestly')
    id_forestly = serializers.CharField(source='id_quarter.id_district_forestly.id_forestly.id')
    forestly = serializers.CharField(source='id_quarter.id_district_forestly.id_forestly')
    subjectrf = serializers.CharField(source='id_quarter.id_district_forestly.id_forestly.id_subject_rf')
    soil_lot = serializers.CharField(max_length=300)


class ListRegionSerializer(serializers.ModelSerializer):
    class Meta:
        model = ListRegion
        fields = "__all__"

    def create(self, validated_data):
        return ListRegion.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.date = validated_data.get("date")
        instance.soil_lot = validated_data.get("soil_lot")
        instance.id_quarter = validated_data.get("id_quarter")
        instance.sample_region = validated_data.get("sample_region")
        instance.mark_del = validated_data.get("mark_del")
        instance.mark_update = 0
        instance.save()
        return instance


class ListRegionSerializerId(serializers.Serializer):
    id = serializers.IntegerField()
    date = serializers.DateField()
    sample_region = serializers.CharField(max_length=300)
    id_quarter = serializers.CharField(source='id_quarter.id')
    id_district_forestly = serializers.CharField(source='id_quarter.id_district_forestly.id')
    id_forestly = serializers.CharField(source='id_quarter.id_district_forestly.id_forestly.id')
    id_subject_rf = serializers.CharField(source='id_quarter.id_district_forestly.id_forestly.id_subject_rf.id')
    mark_del = serializers.BooleanField()
    mark_update = serializers.BooleanField()
    soil_lot = serializers.CharField(max_length=300)
    number_region = serializers.CharField(max_length=100)


class ListRegionFiltersSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    date = serializers.DateField()
    sample_region = serializers.CharField(max_length=300)
    id_quarter = serializers.CharField(source='id_quarter.id_district_forestly.id_forestly.id_subject_rf.id')
    mark_del = serializers.BooleanField()
    mark_del = serializers.BooleanField()
    soil_lot = serializers.CharField(max_length=300)


class AllListRegionSerializer(serializers.Serializer):
    listregion = ListRegionSerializer(many=True)
    quarter = QuarterSerializer(many=True)
    district_forestly = DistrictForestlySerializer(many=True)
    forestly = ForestlySerializer(many=True)
    subjectrf = SubjectRFSerializer(many=True)


class NameOfDistrictSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    name_district_forestly = serializers.CharField()


class NameOfForestlySerializer(serializers.Serializer):
    id = serializers.IntegerField()
    name_forestly = serializers.CharField()


class QuarterOutDistrict(serializers.Serializer):
    id = serializers.IntegerField()
    quarter_name = serializers.CharField()


class AllForestSerializer(serializers.Serializer):
    district_forestly = NameOfDistrictSerializer(many=True)
    forestly = NameOfForestlySerializer(many=True)
    subjectrf = SubjectRFSerializer(many=True)
    quarter = QuarterOutDistrict(many=True)


class GetDocumentListSerializer(serializers.ModelSerializer):
    # id = serializers.IntegerField()
    id_breed = serializers.CharField(source='id_breed.id')
    id_sample = serializers.CharField(source='id_sample.id')
    id_type_of_reproduction = serializers.CharField(source='id_type_of_reproduction.id')
    # # breed = serializers.CharField(source='id_breed.name_breed')
    # to0_2 = serializers.IntegerField()
    # from0_21To0_5 = serializers.IntegerField()
    # from0_6To1_0 = serializers.IntegerField()
    # from1_1to1_5 = serializers.IntegerField()
    # from1_5 = serializers.IntegerField()
    # max_height = serializers.IntegerField()

    class Meta:
        model = List
        fields = '__all__'


class GetGPS(serializers.Serializer):
    id = serializers.IntegerField()
    id_sample = serializers.CharField(source='id_sample.id')
    latitude = serializers.FloatField()
    longitude = serializers.FloatField()
    flag_center = serializers.IntegerField()


class GetFromSampleProfileSerializer(serializers.Serializer):
    id = serializers.IntegerField(source='id_profile.id')
    FIO = serializers.CharField(source='id_profile.FIO')


class GetSampleListDataSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    id_list_region = serializers.CharField()
    id_quarter = serializers.CharField(source='id_quarter.id')
    id_district_forestly = serializers.CharField(source='id_quarter.id_district_forestly.id')
    id_forestly = serializers.CharField(source='id_quarter.id_district_forestly.id_forestly.id')
    id_subjectrf = serializers.CharField(source='id_quarter.id_district_forestly.id_forestly.id_subject_rf.id')
    date = serializers.DateField()
    sample_area = serializers.FloatField()
    id_profile = serializers.CharField(source='id_profile.id')
    profile = serializers.CharField(source='id_profile.FIO')
    soil_lot = serializers.CharField()


class GetAllSampleListDataSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    id_list_region = serializers.CharField()
    id_quarter = serializers.CharField(source='id_quarter.id')
    id_district_forestly = serializers.CharField(source='id_quarter.id_district_forestly.id')
    id_forestly = serializers.CharField(source='id_quarter.id_district_forestly.id_forestly.id')
    id_subjectrf = serializers.CharField(source='id_quarter.id_district_forestly.id_forestly.id_subject_rf.id')
    date = serializers.DateField()
    sample_area = serializers.FloatField()
    id_profile = serializers.CharField(source='id_profile.id')
    profile = serializers.CharField(source='id_profile.FIO')
    soil_lot = serializers.CharField()


class GetForestlyBySubjectRFIdSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    name_forestly = serializers.CharField()


class GetDistrictForestlyByForestlyIdSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    name_district_forestly = serializers.CharField()


class GetQuarterByDistrictForestlyIdSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    quarter_name = serializers.CharField()


class GetAllListRegionDataSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    date = serializers.DateField()
    sample_region = serializers.CharField()
    id_quarter = serializers.CharField(source='id_quarter.id')
    id_district_forestly = serializers.IntegerField(source="id_quarter.id_district_forestly.id")
    id_forestly = serializers.IntegerField(source="id_quarter.id_district_forestly.id_forestly.id")
    id_subject_rf = serializers.IntegerField(source="id_quarter.id_district_forestly.id_forestly.id_subject_rf.id")
    mark_del = serializers.BooleanField()
    soil_lot = serializers.CharField()


class PhotoPointSerializer(serializers.ModelSerializer):

    class Meta:
        model = PhotoPoint
        fields = '__all__'
        extra_kwargs = {
            'latitude': {'required': True},
            'photo': {'required': True},
            'id_sample': {'required':True},
            'date': {'required': True},
            'longitude': {'required': True}
        }

    def create(self, validated_data):
        return PhotoPoint.objects.create(**validated_data)


    def validate_sample(self, value):
        try:
            lst = PhotoPoint.objects.filter(id_sample=value).get()
            return value
        except:
            raise serializers.ValidationError("id_sample not found")

class PhotoPointSer(serializers.ModelSerializer):

    class Meta:
        model = PhotoPoint
        fields = '__all__'

class DescriptionRegionSerializer(serializers.ModelSerializer):
    id_quarter = serializers.CharField(source='id_list_region.id_quarter.id')
    id_district_forestly = serializers.IntegerField(source="id_list_region.id_quarter.id_district_forestly.id")
    id_forestly = serializers.IntegerField(source="id_list_region.id_quarter.id_district_forestly.id_forestly.id")
    id_subject_rf = serializers.IntegerField(source="id_list_region.id_quarter.id_district_forestly.id_forestly.id_subject_rf.id")
    soil_lot = serializers.CharField(source="id_list_region.soil_lot")
    sample_region = serializers.FloatField(source="id_list_region.sample_region")
    date = serializers.DateField(source="id_list_region.date")
    class Meta:
        model = DescriptionRegion
        fields = '__all__'

    def validate_pk(self, value):
        try:
            DescriptionRegion.objects.get(id=value)
            return value
        except:
            raise serializers.ValidationError("pk not found")


class FieldCardSerializer(serializers.ModelSerializer):
    id_quarter = serializers.CharField(source='id_list_region.id_quarter.id')
    id_district_forestly = serializers.IntegerField(source="id_list_region.id_quarter.id_district_forestly.id")
    id_forestly = serializers.IntegerField(source="id_list_region.id_quarter.id_district_forestly.id_forestly.id")
    id_subject_rf = serializers.IntegerField(source="id_list_region.id_quarter.id_district_forestly.id_forestly.id_subject_rf.id")
    soil_lot = serializers.CharField(source="id_list_region.soil_lot")
    sample_region = serializers.FloatField(source="id_list_region.sample_region")
    date = serializers.DateField(source="id_list_region.date")
    class Meta:
        model = FieldСard
        fields = '__all__'


    def validate_pk(self, value):
        try:
            FieldСard.objects.get(id=value)
            return value
        except:
            raise serializers.ValidationError("pk not found")