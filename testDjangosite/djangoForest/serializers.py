import io

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
        instance.to0_2 = validated_data.get("to0_2")
        instance.from0_21To0_5 = validated_data.get("from0_21To0_5")
        instance.from0_6To1_0 = validated_data.get("from0_6To1_0")
        instance.from1_1to1_5 = validated_data.get("from1_1to1_5")
        instance.from1_5 = validated_data.get("from1_5")
        instance.max_height = validated_data.get("max_height")
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
    class Meta:
        model = Sample
        fields = '__all__'

    def create(self, validated_data):
        return Sample.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.id_list_region = validated_data.get("id_list_region")
        instance.sample_area = validated_data.get("sample_area")
        instance.id_profile = validated_data.get("id_profile")
        instance.soil_lot = validated_data.get("soil_lot")
        instance.id_quarter = validated_data.get("id_quarter")
        instance.save()
        return instance


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
        instance.save()
        return instance


class ListRegionSerializerId(serializers.Serializer):
    id = serializers.IntegerField()
    date = serializers.DateField()
    sample_region = serializers.CharField(max_length=300)
    id_quarter = serializers.CharField(source='id_quarter.id')
    id_district_forestly = serializers.CharField(source='id_quarter.id_district_forestly.id')
    id_forestly = serializers.CharField(source='id_quarter.id_district_forestly.id_forestly.id')
    id_subjectrf = serializers.CharField(source='id_quarter.id_district_forestly.id_forestly.id_subject_rf.id')
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


class GetDocumentListSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    id_breed = serializers.CharField(source='id_breed.id')
    id_sample = serializers.CharField(source='id_sample.id')
    id_type_of_reproduction = serializers.CharField(source='id_type_of_reproduction.id')
    # breed = serializers.CharField(source='id_breed.name_breed')
    to0_2 = serializers.IntegerField()
    from0_21To0_5 = serializers.IntegerField()
    from0_6To1_0 = serializers.IntegerField()
    from1_1to1_5 = serializers.IntegerField()
    from1_5 = serializers.IntegerField()
    max_height = serializers.IntegerField()


class GetGPS(serializers.Serializer):
    id = serializers.IntegerField()
    latitude = serializers.FloatField()
    longitude = serializers.FloatField()
    flag_center = serializers.IntegerField()


class GetFromSampleProfileSerializer(serializers.Serializer):
    id = serializers.IntegerField(source='id_profile.id')
    FIO = serializers.CharField(source='id_profile.FIO')


class GetSampleListDataSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    id_list_region = serializers.IntegerField()
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
    sample_region = serializers.CharField()
    id_quarter = serializers.CharField()
    id_district_forestly = serializers.IntegerField(source="id_quarter.id_district_forestly.id")
    id_forestly = serializers.IntegerField(source="id_quarter.id_district_forestly.id_forestly.id")
    id_subject_rf = serializers.IntegerField(source="id_quarter.id_district_forestly.id_forestly.id")
    soil_lot = serializers.CharField()

