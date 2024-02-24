from django.contrib import admin
from .models import *
# Register your models here.


class TodoAdmin(admin.ModelAdmin):
    list_display = ('id', 'name', 'age')
    list_display_links = ('id', 'name', 'age')
    search_fields = ('id', 'name', 'age')


class ForestDistrictsAdmin(admin.ModelAdmin):
    list_display = '__all__'
    list_display_links = '__all__'
    search_fields = '__all__'


class RecommendationAdmin(admin.ModelAdmin):
    list_display = '__all__'
    list_display_links = '__all__'
    search_fields = '__all__'


class ForestCareActivitiesAdmin(admin.ModelAdmin):
    list_display = '__all__'
    list_display_links = '__all__'
    search_fields = '__all__'


class MethodForestRestorationAdmin(admin.ModelAdmin):
    list_display = '__all__'
    list_display_links = '__all__'
    search_fields = '__all__'


class FederalDistrictsAdmin(admin.ModelAdmin):
    list_display = '__all__'
    list_display_links = '__all__'
    search_fields = '__all__'

class PlotCoeffAdmin(admin.ModelAdmin):
    list_display = '__all__'
    list_display_links = '__all__'
    search_fields = '__all__'

class DescriptionRegionAdmin(admin.ModelAdmin):
    list_display = '__all__'
    list_display_links = '__all__'
    search_fields = '__all__'

class FieldCardAdmin(admin.ModelAdmin):
    list_display = '__all__'
    list_display_links = '__all__'
    search_fields = '__all__'

class UserAdmin(admin.ModelAdmin):
    list = ('id', 'email')
    list_display = ('id', 'email', 'password')
    list_display_links = ('id', 'email', 'password')

class ProfileAdmin(admin.ModelAdmin):
    list_display = ('id', 'FIO', 'phoneNumber', 'id_post', 'id_role', 'id_branches')
    list_display_links = ('id', 'FIO', 'phoneNumber', 'id_post', 'id_role', 'id_branches')
    search_fields = ('id', 'FIO', 'phoneNumber', 'id_post', 'id_role', 'id_branches')


class ListAdmin(admin.ModelAdmin):
    list_display = ('id', 'id_sample', 'id_breed', 'id_type_of_reproduction', 'to0_2', 'from0_21To0_5', 'from0_6To1_0', 'from1_1to1_5')
    list_display_links = ('id', 'id_sample', 'id_breed', 'id_type_of_reproduction', 'to0_2', 'from0_21To0_5', 'from0_6To1_0', 'from1_1to1_5')
    search_fields = ('id', 'id_sample', 'id_breed', 'id_type_of_reproduction', 'to0_2', 'from0_21To0_5', 'from0_6To1_0', 'from1_1to1_5')


class GPSAdmin(admin.ModelAdmin):
    list_display = ('id', 'id_sample', 'latitude', 'longitude', 'flag_center')
    list_display_links = ('id', 'id_sample', 'latitude', 'longitude', 'flag_center')
    search_fields = ('id', 'id_sample', 'latitude', 'longitude', 'flag_center')

class InlineListRegion(admin.TabularInline):
    model = ListRegion
    extra = 1

class ListRegionAdmin(admin.ModelAdmin):
    list_display = ('id', 'sample_region', 'date', 'soil_lot', 'mark_update')
    list_display_links = ('id', 'sample_region', 'date', 'soil_lot', 'mark_update')
    search_fields = ('id', 'sample_region', 'date', 'soil_lot', 'mark_update')


class SampleAdmin(admin.ModelAdmin):
    list_display = ('id', 'date', 'sample_area', 'id_profile', 'id_list_region', 'soil_lot')
    list_display_links = ('id', 'date', 'sample_area', 'id_profile', 'id_list_region', 'soil_lot')
    search_fields = ('id', 'date', 'sample_area', 'id_profile', 'id_list_region', 'soil_lot')


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
    list_display = ('id', 'name_forestly', 'id_subject_rf')
    list_display_links = ('id', 'name_forestly', 'id_subject_rf')
    search_fields = ('id', 'name_forestly', 'id_subject_rf')


class DistrictForestlyAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_district_forestly', 'id_forestly')
    list_display_links = ('id', 'name_district_forestly', 'id_forestly')
    search_fields = ('id', 'name_district_forestly', 'id_forestly')


class BreedAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_breed')
    list_display_links = ('id', 'name_breed')
    search_fields = ('id', 'name_breed')


class BranchesAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_branch')
    list_display_links = ('id', 'name_branch')
    search_fields = ('id', 'name_branch')

class QuarterAdmin(admin.ModelAdmin):
    list_display = ('id', 'quarter_name', 'id_district_forestly')
    list_display_links = ('id', 'quarter_name', 'id_district_forestly')
    search_fields = ('id', 'quarter_name', 'id_district_forestly')


class PurposeOfForestsAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_purpose')
    list_display_links = ('id', 'name_purpose')
    search_fields = ('id', 'name_purpose')


class ForestProtectionCategoryAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_forest_protection_category')
    list_display_links = ('id', 'name_forest_protection_category')
    search_fields = ('id', 'name_forest_protection_category')


class CategoryOfForestFundLandsAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_category')
    list_display_links = ('id', 'name_category')
    search_fields = ('id', 'name_category')


class MethodOfReforestationAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_of_method')
    list_display_links = ('id', 'name_of_method')
    search_fields = ('id', 'name_of_method')


class BonitetOrlovAdmin(admin.ModelAdmin):
    list_display = ('id', 'age_of_planting', 'height_planting_for_bonitet_class', 'class_bonitet')
    list_display_links = ('id', 'age_of_planting', 'height_planting_for_bonitet_class', 'class_bonitet')
    search_fields = ('id', 'age_of_planting', 'height_planting_for_bonitet_class', 'class_bonitet')


class TypeForestGrowingConditionsAdmin(admin.ModelAdmin):
    list_display = ('id', 'subtypes_of_humidity', 'subtypes_of_rich', 'type_forest_growing_conditions')
    list_display_links = ('id', 'subtypes_of_humidity', 'subtypes_of_rich', 'type_forest_growing_conditions')
    search_fields = ('id', 'subtypes_of_humidity', 'subtypes_of_rich', 'type_forest_growing_conditions')


class EconomyAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_economy')
    list_display_links = ('id', 'name_economy')
    search_fields = ('id', 'name_economy')


class AccordanceMolodKrAndTPPLAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_of_accordance')
    list_display_links = ('id', 'name_of_accordance')
    search_fields = ('id', 'name_of_accordance')


class AccordanceNoneAccordanceEconomyAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_accordance_none_economy')
    list_display_links = ('id', 'name_accordance_none_economy')
    search_fields = ('id', 'name_accordance_none_economy')


class CategoryGroundLFInNoneAccordanceAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_of_category_ground')
    list_display_links = ('id', 'name_of_category_ground')
    search_fields = ('id', 'name_of_category_ground')


class ForestAreasAdmin(admin.ModelAdmin):
    list_display = ('id', 'name_forest_areas', 'composition_of_forest_areas', 'comm')
    list_display_links = ('id', 'name_forest_areas', 'composition_of_forest_areas', 'comm')
    search_fields = ('id', 'name_forest_areas', 'composition_of_forest_areas', 'comm')


class PhotoPointAdmin(admin.ModelAdmin):
    list_display = ('id', 'photo', 'id_sample')
    list_display_links = ('id', 'photo', 'id_sample')
    search_fields = ('id', 'photo', 'id_sample')


class TrackingAdmin(admin.ModelAdmin):
    list_display = ('id', 'id_profile', 'data', 'map')
    list_display_links = ('id', 'id_profile', 'data', 'map')
    search_fields = ('id', 'id_profile', 'data', 'map')


admin.site.register(MethodForestRestoration)
admin.site.register(ForestCareActivities)
admin.site.register(Recommendation)
admin.site.register(ForestDistricts)
admin.site.register(FederalDistricts)
admin.site.register(PlotCoeff)
admin.site.register(DescriptionRegion)
admin.site.register(FieldCard)
admin.site.register(Users, UserAdmin)
admin.site.register(Track, TrackingAdmin)
admin.site.register(PhotoPoint, PhotoPointAdmin)
admin.site.register(ForestAreas, ForestAreasAdmin)
admin.site.register(PurposeOfForests, PurposeOfForestsAdmin)    # Целевое назначение лесов
admin.site.register(ForestProtectionCategory, ForestProtectionCategoryAdmin)    # Категория защитности лесов
admin.site.register(CategoryOfForestFundLands, CategoryOfForestFundLandsAdmin)  # Категория земель лесного фонда
admin.site.register(MethodOfReforestation, MethodOfReforestationAdmin)  # Способ лесовосстановления
admin.site.register(BonitetOrlov, BonitetOrlovAdmin)    # Бонитет по Орлову
admin.site.register(TypeForestGrowingConditions, TypeForestGrowingConditionsAdmin)  # Тип лесорастительный условий
admin.site.register(Economy, EconomyAdmin)  # Хозяйство
admin.site.register(AccordanceMolodKrAndTPPL, AccordanceMolodKrAndTPPLAdmin)    # Соответ. молодняка кр. и тр. ПЛ
admin.site.register(AccordanceNoneAccordanceEconomy, AccordanceNoneAccordanceEconomyAdmin)  # Соответ. не соответ. хозяйству
admin.site.register(CategoryGroundLFInNoneAccordance, CategoryGroundLFInNoneAccordanceAdmin)    # Кат. земель лф в случ. несоотв.
admin.site.register(Quarter, QuarterAdmin)  # Квартал
admin.site.register(Profile, ProfileAdmin)  # Профиль
admin.site.register(GPS, GPSAdmin)  # GPS
admin.site.register(List, ListAdmin)
admin.site.register(ListRegion, ListRegionAdmin)
admin.site.register(Sample, SampleAdmin)    # Проба
admin.site.register(Post, PostAdmin)    # Должность
admin.site.register(WorkingBreeds, WorkingBreedsAdmin)  # Рабочие породы
admin.site.register(SubjectRF, SubjectRFAdmin)  # Субъект РФ
admin.site.register(Role, RoleAdmin)    # Роль
admin.site.register(Reproduction, ReproductionAdmin)  # Воспроизводство
admin.site.register(Forestly, ForestlyAdmin)    # Лесничество
admin.site.register(DistrictForestly, DistrictForestlyAdmin)    # Участковое лесничество
admin.site.register(Breed, BreedAdmin)  # Участок
admin.site.register(Branches, BranchesAdmin)    # Филиал
