from django.contrib import admin
from .models import *
# Register your models here.


class TodoAdmin(admin.ModelAdmin):
    list_display = ('id', 'name', 'age')
    list_display_links = ('id', 'name', 'age')
    search_fields = ('id', 'name', 'age')


class ProfileAdmin(admin.ModelAdmin):
    list_display = ('id', 'FIO', 'phoneNumber', 'email', 'id_post', 'id_working_breeds', 'id_role', 'id_branches')
    list_display_links = ('id', 'FIO', 'phoneNumber', 'email', 'id_post', 'id_working_breeds', 'id_role', 'id_branches')
    search_fields = ('id', 'FIO', 'phoneNumber', 'email', 'id_post', 'id_working_breeds', 'id_role', 'id_branches')

class ListAdmin(admin.ModelAdmin):
    list_display = ('id', 'id_sample', 'id_breed', 'id_type_of_reproduction', 'to0_2', 'from0_21To0_5', 'from0_6To1_0', 'from1_1to1_5')
    list_display_links = ('id', 'id_sample', 'id_breed', 'id_type_of_reproduction', 'to0_2', 'from0_21To0_5', 'from0_6To1_0', 'from1_1to1_5')
    search_fields = ('id', 'id_sample', 'id_breed', 'id_type_of_reproduction', 'to0_2', 'from0_21To0_5', 'from0_6To1_0', 'from1_1to1_5')


class GPSAdmin(admin.ModelAdmin):
    list_display = ('id', 'id_sample', 'latitude', 'longitude', 'flag_center')
    list_display_links = ('id', 'id_sample', 'latitude', 'longitude', 'flag_center')
    search_fields = ('id', 'id_sample', 'latitude', 'longitude', 'flag_center')


class RegionAdmin(admin.ModelAdmin):
    list_display = ('id', 'id_subject_rf', 'id_forestly', 'id_district_forestly', 'quarter', 'soil_lot', 'sample_region')
    list_display_links = ('id', 'id_subject_rf', 'id_forestly', 'id_district_forestly', 'quarter', 'soil_lot', 'sample_region')
    search_fields = ('id', 'id_subject_rf', 'id_forestly', 'id_district_forestly', 'quarter', 'soil_lot', 'sample_region')


class ListRegionAdmin(admin.ModelAdmin):
    list_display = ('id', 'id_region', 'date')
    list_display_links = ('id', 'id_region', 'date')
    search_fields = ('id', 'id_region', 'date')


class SampleAdmin(admin.ModelAdmin):
    list_display = ('id', 'id_list_region', 'sample_area', 'id_profile')
    list_display_links = ('id', 'id_list_region', 'sample_area', 'id_profile')
    search_fields = ('id', 'id_list_region', 'sample_area', 'id_profile')


class PostAdmin(admin.ModelAdmin):
    list_display = ('id', 'post_name')
    list_display_links = ('id', 'post_name')
    search_fields = ('id', 'post_name')


class WorkingBreedsAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_breeds')
    list_display_links = ('id', 'name_breeds')
    search_fields = ('id', 'name_breeds')


class SubjectRFAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_subject_RF')
    list_display_links = ('id', 'name_subject_RF')
    search_fields = ('id', 'name_subject_RF')


class RoleAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_role')
    list_display_links = ('id', 'name_role')
    search_fields = ('id', 'name_role')


class ReproductionAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_reproduction')
    list_display_links = ('id', 'name_reproduction')
    search_fields = ('id', 'name_reproduction')


class ForestlyAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_forestly')
    list_display_links = ('id', 'name_forestly')
    search_fields = ('id', 'name_forestly')


class DistrictForestlyAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_district_forestly')
    list_display_links = ('id', 'name_district_forestly')
    search_fields = ('id', 'name_district_forestly')


class BreedAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_breed')
    list_display_links = ('id', 'name_breed')
    search_fields = ('id', 'name_breed')


class BranchesAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_branch', 'id_subject_RF')
    list_display_links = ('id', 'name_branch', 'id_subject_RF')
    search_fields = ('id', 'name_branch', 'id_subject_RF')


admin.site.register(Profile, ProfileAdmin)#Профиль
admin.site.register(GPS, GPSAdmin)#GPS
admin.site.register(Region, RegionAdmin)
admin.site.register(List, ListAdmin)
admin.site.register(ListRegion, ListRegionAdmin)
admin.site.register(Sample, SampleAdmin)#Проба
admin.site.register(Post, PostAdmin)#Должность
admin.site.register(WorkingBreeds, WorkingBreedsAdmin)#Рабочие породы
admin.site.register(SubjectRF, SubjectRFAdmin)#Субъект РФ
admin.site.register(Role, RoleAdmin)#Роль
admin.site.register(Reproduction, ReproductionAdmin)#Воспроизводство
admin.site.register(Forestly, ForestlyAdmin)#Лесничество
admin.site.register(DistrictForestly, DistrictForestlyAdmin)#Участковое лесничество
admin.site.register(Breed, BreedAdmin)  #Участок
admin.site.register(Branches, BranchesAdmin) # Филиал