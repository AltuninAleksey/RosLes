import io

from django.contrib.auth.hashers import make_password
from rest_framework import serializers, status
from .models import *
from django.core.validators import validate_email as val_email
from django.core.exceptions import ValidationError
# import django.core.exceptions
from django.core.validators import EmailValidator

class TableSerializer(serializers.ModelSerializer):
    class Meta:
        model = Table
        fields = '__all__'

def non_blank(value):
    return f"Поле {value} не может быть пустым"

class ProfileSerializer(serializers.ModelSerializer):
    id_subject_rf = serializers.CharField(source="id_user.subject_rf.id", read_only=True)

    class Meta:
        model = Profile
        fields = '__all__'
        extra_kwargs = {
            "FIO": {"error_messages": {'blank': "Поле ФИО не может быть пустым"}},
            "phoneNumber": {"error_messages": {'blank': "Поле номер телефона не может быть пустым"}}
        }

    def create(self, validated_data):
        return Profile.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.FIO = validated_data.get("FIO")
        instance.phoneNumber = validated_data.get("phoneNumber")
        instance.id_post = validated_data.get("id_post")
        instance.id_role = validated_data.get("id_role")
        instance.id_branches = validated_data.get("id_branches")
        instance.save()
        return instance

    def validate(self, data):

        if data['FIO']:
            for i in data['FIO']:
                if i.isdigit():
                    raise serializers.ValidationError({"error_text": "В ФИО не могут находиться цифры"})

        if data['phoneNumber']:
            for i in data['phoneNumber']:
                if i.isalpha():
                    raise serializers.ValidationError({"error_text": "Номер телефона должен состоять только из цифр."})

        return data


class ProfileWithSubjectSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    id_subject_rf = serializers.CharField(source="id_user.subject_rf.id", read_only = True)


class UserSerializer(serializers.ModelSerializer):

    class Meta:
        model = Users
        fields = '__all__'
        extra_kwargs = {
            "email": {"error_messages": {'blank': "Поле email не может быть пустым"}},
            "password": {"error_messages": {'blank': "Поле password не может быть пустым"}},
            "subject_rf_id": {"error_messages": {'bkank': "Поле subject_rf не может быть пустым"}},
        }

    def create(self, validated_data):
        return Users.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.email = validated_data.get("email")
        instance.password = validated_data.get("password"), "pbkdf2_sha256"
        instance.save()


    # def validate(self, data):

        # if data['email']:
        #     if len(data["email"]) == 0:
        #         raise serializers.ValidationError({"error_text": "email не должен быть пустым"})
        # if data['password']:
        #     if len(data["password"]) == 0:
        #         raise serializers.ValidationError({"error_text": "password не должен быть пустым"})
        # return data

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
        # instance.id_quarter = validated_data.get("id_quarter")
        instance.id_list_region = validated_data.get("id_list_region")
        instance.width = validated_data.get("width")
        instance.lenght = validated_data.get("lenght")
        instance.square = validated_data.get("square")
        instance.mark_update = 0
        instance.save()
        return instance

class AboutUserDataSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    id_user = serializers.CharField(source="id_user.id")
    FIO = serializers.CharField()
    id_subject_rf = serializers.CharField(source="id_user.subject_rf.id")
    name_subject_rf = serializers.CharField(source="id_user.subject_rf.name_subject_RF")


class SampleSerializerId(serializers.Serializer):
    id = serializers.IntegerField()
    date = serializers.DateField()
    sample_area = serializers.FloatField()
    id_profile = serializers.CharField()
    # id_quarter = serializers.CharField()
    id_list_region = serializers.CharField()
    id_district_forestly = serializers.CharField(source='id_list_region.id_district_forestly.id')
    id_forestly = serializers.CharField(source='id_list_region.id_district_forestly.id_forestly.id')
    id_subject_rf = serializers.CharField(source='id_list_region.id_district_forestly.id_forestly.id_subject_rf.id')
    dacha = serializers.CharField(source='id_list_region.dacha')
    name_quarter = serializers.CharField(source='id_list_region.name_quarter')
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
        extra_kwargs = {
            "name_forestly": {"error_messages": {'blank': "Поле forestly не может быть пустым"}},
            "id_subject_rf": {"error_messages": {'blank': "Поле id_subject_rf не может быть пустым"}}
        }


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
        extra_kwargs = {
            "name_forestly": {"error_messages": {'blank': "Поле forestly не может быть пустым"}},
            "id_subject_rf": {"error_messages": {'blank': "Поле id_subject_rf не может быть пустым"}}
        }

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
        extra_kwargs = {
            "quarter_name": {"error_messages": {'blank': "Поле quarter_name не может быть пустым"}},
            "id_district_forestly": {"error_messages": {'blank': "Поле id_district_forestly не может быть пустым"}}
        }

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
        # instance.id_quarter = validated_data.get("id_quarter")
        instance.id_district_forestly = validated_data.get("id_district_forestly")
        instance.dacha = validated_data.get("dacha")
        instance.name_quarter =validated_data.get("name_quarter")
        instance.sample_region = validated_data.get("sample_region")
        instance.mark_del = validated_data.get("mark_del")
        instance.number_region = validated_data.get("number_region")
        instance.mark_update = 0
        instance.id_profile = validated_data.get("id_profile")
        instance.save()
        return instance


class ListRegionUpdateSerializer(serializers.ModelSerializer):
    id_profile = serializers.CharField(required=False)
    class Meta:
        model = ListRegion
        fields = "__all__"

    def update(self, instance, validated_data):
        instance.date = validated_data.get("date")
        instance.soil_lot = validated_data.get("soil_lot")
        # instance.id_quarter = validated_data.get("id_quarter")
        instance.id_district_forestly = validated_data.get("id_district_forestly")
        instance.dacha = validated_data.get("dacha")
        instance.name_quarter = validated_data.get("name_quarter")
        instance.sample_region = validated_data.get("sample_region")
        instance.mark_del = validated_data.get("mark_del")
        instance.number_region = validated_data.get("number_region")
        instance.mark_update = validated_data.get("mark_update")
        # instance.id_profile = validated_data.get("id_profile")
        instance.save()
        return instance


class ListRegionUpdateNonMarkDel(serializers.ModelSerializer):

    class Meta:
        model = ListRegion
        fields = '__all__'


    def update(self, instance, validated_data):
        instance.date = validated_data.get("date")
        instance.soil_lot = validated_data.get("soil_lot")
        instance.id_district_forestly = validated_data.get("id_district_forestly")
        instance.name_quarter = validated_data.get("name_quarter")
        instance.dacha = validated_data.get("dacha")
        instance.sample_region = validated_data.get("sample_region")
        instance.number_region = validated_data.get("number_region")
        instance.save()
        return instance


class ListRegionSerializerId(serializers.Serializer):
    id = serializers.IntegerField()
    date = serializers.DateField()
    sample_region = serializers.CharField(max_length=300)
    dacha = serializers.CharField()
    id_district_forestly = serializers.CharField(source='id_district_forestly.id')
    id_forestly = serializers.CharField(source='id_district_forestly.id_forestly.id')
    id_subject_rf = serializers.CharField(source='id_district_forestly.id_forestly.id_subject_rf.id')
    mark_del = serializers.BooleanField()
    mark_update = serializers.BooleanField()
    soil_lot = serializers.CharField(max_length=300)
    number_region = serializers.CharField(max_length=100)
    name_quarter = serializers.CharField()


class ListRegionFiltersSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    date = serializers.DateField()
    sample_region = serializers.CharField(max_length=300)
    # id_quarter = serializers.CharField(source='id_quarter.id_district_forestly.id_forestly.id_subject_rf.id')
    mark_del = serializers.BooleanField()
    mark_update = serializers.BooleanField()
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


class GetDocumentListSerializerNonNull(serializers.ModelSerializer):

    class Meta:
        model = List
        fields = ('id', 'count_of_plants', 'avg_height', 'id_undergrowth', 'id_breed')


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
    id_district_forestly = serializers.CharField(source='id_list_region.id_district_forestly.id')
    id_forestly = serializers.CharField(source='id_list_region.id_district_forestly.id_forestly.id')
    id_subjectrf = serializers.CharField(source='id_list_region.id_district_forestly.id_forestly.id_subject_rf.id')
    dacha = serializers.CharField(source='id_list_region.dacha')
    name_quarter = serializers.CharField(source='id_list_region.name_quarter')
    date = serializers.DateField()
    sample_area = serializers.FloatField()
    id_profile = serializers.CharField(source='id_profile.id')
    profile = serializers.CharField(source='id_profile.FIO')
    soil_lot = serializers.CharField()
    number_region = serializers.CharField(source='id_list_region.number_region')


class GetAllSampleListDataSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    id_list_region = serializers.CharField()
    # id_quarter = serializers.CharField(source='id_quarter.id')
    id_district_forestly = serializers.CharField(source='id_list_region.id_district_forestly.id')
    id_forestly = serializers.CharField(source='id_list_region.id_district_forestly.id_forestly.id')
    id_subjectrf = serializers.CharField(source='id_list_region.id_district_forestly.id_forestly.id_subject_rf.id')
    dacha = serializers.CharField(source='id_list_region.dacha')
    name_quarter = serializers.CharField(source='id_list_region.name_quarter')
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
    id_forestly = serializers.CharField(source="id_forestly.id")


class GetQuarterByDistrictForestlyIdSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    quarter_name = serializers.CharField()
    id_district_forestly = serializers.CharField(source="id_district_forestly.id")


class GetAllListRegionDataSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    date = serializers.DateField()
    sample_region = serializers.CharField()
    # id_quarter = serializers.CharField(source='id_quarter.id')
    name_quarter = serializers.CharField()
    dacha = serializers.CharField()
    id_district_forestly = serializers.IntegerField(source="id_district_forestly.id")
    id_forestly = serializers.IntegerField(source="id_district_forestly.id_forestly.id")
    id_subject_rf = serializers.IntegerField(source="id_district_forestly.id_forestly.id_subject_rf.id")
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
    # id_quarter = serializers.CharField(source='id_list_region.id_quarter.id')
    id_district_forestly = serializers.IntegerField(source="id_list_region.id_district_forestly.id", read_only=True)
    id_forestly = serializers.IntegerField(source="id_list_region.id_district_forestly.id_forestly.id", read_only=True)
    id_subject_rf = serializers.IntegerField(
        source="id_list_region.id_district_forestly.id_forestly.id_subject_rf.id", read_only=True)
    soil_lot = serializers.CharField(source="id_list_region.soil_lot", read_only=True)
    sample_region = serializers.FloatField(source="id_list_region.sample_region", read_only=True)
    date = serializers.DateField(source="id_list_region.date", read_only=True)
    # id_list_region = serializers.CharField(source="id_list_region.id")
    number_region = serializers.CharField(source="id_list_region.number_region", read_only=True)
    dacha = serializers.CharField(source="id_list_region.dacha")
    name_quarter = serializers.CharField(source="id_list_region.name_quarter")
    class Meta:
        model = DescriptionRegion
        fields = '__all__'


    def validate_pk(self, value):
        try:
            DescriptionRegion.objects.get(id=value)
            return value
        except:
            raise serializers.ValidationError("pk not found")


    def update(self, instance, validated_data):
        instance.id_list_region = validated_data.get('id_list_region')
        instance.id_method_of_reforestation = validated_data.get("id_method_of_reforestation")
        instance.year_assignment_land = validated_data.get("year_assignment_land")
        instance.year_format_fond_trees = validated_data.get("year_format_fond_trees")
        instance.inf_restore_forest = validated_data.get("inf_restore_forest")
        instance.breed_structure_sapling_act_land = validated_data.get("breed_structure_sapling_act_land")
        instance.economy_act_land = validated_data.get("economy_act_land")
        instance.change_breed_and_structure_sapling = validated_data.get('change_breed_and_structure_sapling')
        instance.results_surtvey = validated_data.get("results_surtvey")
        instance.recommendation = validated_data.get("recommendation")
        instance.id_schema_mixing_breeds = validated_data.get("id_schema_mixing_breeds")
        instance.count_plants = validated_data.get("count_plants")
        instance.preservation_breed = validated_data.get("preservation_breed")
        instance.farm_according_data_survey = validated_data.get("farm_according_data_survey")
        instance.breed_composition_sapling_data_surver = validated_data.get("breed_composition_sapling_data_surver")
        # instance.id_quarter = validated_data.get("id_quarter")
        instance.save()
        return instance


class DescriptionRegionSerializerNonEconomyAct(serializers.ModelSerializer):
    # id_quarter = serializers.CharField(source='id_list_region.id_quarter.id')
    name_quarter = serializers.CharField(source='id_list_region.name_quarter', read_only = True)
    dacha = serializers.CharField(source='id_list_region.dacha', read_only = True)
    id_district_forestly = serializers.IntegerField(source="id_list_region.id_quarter.id_district_forestly.id", read_only=True)
    id_forestly = serializers.IntegerField(source="id_list_region.id_quarter.id_district_forestly.id_forestly.id", read_only=True)
    id_subject_rf = serializers.IntegerField(
        source="id_list_region.id_quarter.id_district_forestly.id_forestly.id_subject_rf.id", read_only=True)
    soil_lot = serializers.CharField(source="id_list_region.soil_lot", read_only=True)
    sample_region = serializers.FloatField(source="id_list_region.sample_region", read_only=True)
    date = serializers.DateField(source="id_list_region.date", read_only=True)
    # id_list_region = serializers.CharField(source="id_list_region.id")
    number_region = serializers.CharField(source="id_list_region.number_region", read_only=True)
    economy_act_land = serializers.CharField(required=False)

    class Meta:
        model = DescriptionRegion
        fields = '__all__'



class FieldCardSerializer(serializers.ModelSerializer):
    # id_quarter = serializers.CharField(source='id_list_region.id_quarter.id')
    id_district_forestly = serializers.IntegerField(source="id_list_region.id_district_forestly.id", read_only=True)
    id_forestly = serializers.IntegerField(source="id_list_region.id_district_forestly.id_forestly.id", read_only=True)
    id_subject_rf = serializers.IntegerField(source="id_list_region.id_district_forestly.id_forestly.id_subject_rf.id", read_only=True)
    soil_lot = serializers.CharField(source="id_list_region.soil_lot", read_only=True)
    sample_region = serializers.FloatField(source="id_list_region.sample_region", read_only=True)
    date = serializers.DateField(source="id_list_region.date", read_only=True)
    number_region = serializers.CharField(source="id_list_region.number_region", read_only=True)
    dacha = serializers.CharField(source = "id_list_region.dacha")
    name_quarter = serializers.CharField(source="id_list_region.name_quarter")

    class Meta:
        model = FieldCard
        fields = '__all__'



    def validate_pk(self, value):
        try:
            FieldCard.objects.get(id=value)
            return value
        except:
            raise serializers.ValidationError({"error": 404, "error_text": "invalid id"})

    def update(self, instance, validated_data):
        instance.id_list_region = validated_data.get('id_list_region')
        instance.id_purpose_of_forests = validated_data.get("id_purpose_of_forests")
        instance.id_forest_protection_category = validated_data.get("id_forest_protection_category")
        instance.protected_areas_of_forests = validated_data.get("protected_areas_of_forests")
        instance.rent_area = validated_data.get("rent_area")
        instance.id_category_of_forest_fund_lands = validated_data.get("id_category_of_forest_fund_lands")
        instance.id_method_of_reforestation = validated_data.get("id_method_of_reforestation")
        instance.time_of_reforestation = validated_data.get('time_of_reforestation')
        instance.id_type_forest_growing_conditions = validated_data.get("id_type_forest_growing_conditions")
        instance.forest_type = validated_data.get("forest_type")
        instance.point7year = validated_data.get("point7year")
        instance.point7date = validated_data.get("point7date")
        instance.point7number = validated_data.get("point7number")
        instance.point7agreed = validated_data.get("point7agreed")
        instance.point7_natural_composition = validated_data.get("point7_natural_composition")
        #
        instance.point7_natural_composition2 = validated_data.get('point7_natural_composition2')
        instance.square_one_sample_area = validated_data.get("square_one_sample_area")
        instance.count_sample_area = validated_data.get("count_sample_area")
        instance.breed_composition = validated_data.get("breed_composition")
        instance.id_economy = validated_data.get("id_economy")
        instance.completeness = validated_data.get("completeness")
        instance.stock = validated_data.get("stock")
        instance.id_point7_table2_sapling = validated_data.get('id_point7_table2_sapling')
        instance.conclusion = validated_data.get("conclusion")
        instance.plot_farm_referring_land = validated_data.get("plot_farm_referring_land")
        instance.recomendation = validated_data.get("recomendation")
        instance.plot_features = validated_data.get("plot_features")
        instance.site_survey = validated_data.get("site_survey")
        instance.in_front = validated_data.get("in_front")
        instance.date_and_time = validated_data.get("date_and_time")
        instance.id_economy_sapling = validated_data.get("id_economy_sapling")
        instance.point7_completeness = validated_data.get("point7_completeness")
        instance.point7_stock = validated_data.get("point7_stock")
        instance.number_order = validated_data.get("number_order")
        instance.lands_other = validated_data.get('lands_other')
        instance.save()
        return instance


class FieldCardSerializerNoneSapling(serializers.ModelSerializer):
    dacha = serializers.CharField(source='id_list_region.dacha', read_only = True)
    name_quarter = serializers.CharField(source='id_list_region.name_quarter', read_only = True)
    id_district_forestly = serializers.IntegerField(source="id_list_region.id_quarter.id_district_forestly.id", read_only=True)
    id_forestly = serializers.IntegerField(source="id_list_region.id_quarter.id_district_forestly.id_forestly.id", read_only=True)
    id_subject_rf = serializers.IntegerField(source="id_list_region.id_quarter.id_district_forestly.id_forestly.id_subject_rf.id", read_only=True)
    soil_lot = serializers.CharField(source="id_list_region.soil_lot", read_only=True)
    sample_region = serializers.FloatField(source="id_list_region.sample_region", read_only=True)
    date = serializers.DateField(source="id_list_region.date", read_only=True)
    number_region = serializers.CharField(source="id_list_region.number_region", read_only=True)
    id_point7table = serializers.CharField(required=False)
    id_point7_table2_sapling = serializers.CharField(required=False)

    class Meta:
        model = FieldCard
        fields = '__all__'



class SchemaMixingBreedsSerializer(serializers.ModelSerializer):

    class Meta:
        model = SchemaMixingBreeds
        fields = "__all__"


class Point7TableSerializer(serializers.ModelSerializer):
    id_breed = serializers.CharField(source="id_list_region_breed.id_breed")
    class Meta:
        model = point7Table
        fields = "__all__"


class Point7TableSaplingSerializer(serializers.ModelSerializer):
    id_breed = serializers.CharField(source="id_list_region_breed.id_breed")
    class Meta:
        model = point7Table2Sapling
        fields = "__all__"


class PurposeOfForestsSerializer(serializers.ModelSerializer):

    class Meta:
        model = PurposeOfForests
        fields = "__all__"


class ForestProtectionCategorySerializer(serializers.ModelSerializer):

    class Meta:
        model = ForestProtectionCategory
        fields = "__all__"


class CategoryOfForestFundLandsSerializer(serializers.ModelSerializer):

    class Meta:
        model = CategoryOfForestFundLands
        fields = "__all__"


class MethodOfReforestationSerializer(serializers.ModelSerializer):

    class Meta:
        model = MethodOfReforestation
        fields = "__all__"


class TypeForestGrowingConditionsSerializer(serializers.ModelSerializer):

    class Meta:
        model = TypeForestGrowingConditions
        fields = "__all__"


class EconomySerializer(serializers.ModelSerializer):

    class Meta:
        model = Economy
        fields = "__all__"


class PlotCoeffSerializer(serializers.ModelSerializer):
    def __init__(self, *args, **kwargs):
        many = kwargs.pop('many', True)
        super(PlotCoeffSerializer, self).__init__(many=many, *args, **kwargs)

    class Meta:
        model = PlotCoeff
        fields = "__all__"


