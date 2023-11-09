from django.db import models
import datetime
from .manager import AccountManager
from django.contrib.auth.models import AbstractBaseUser, BaseUserManager, PermissionsMixin
from django.contrib.auth.hashers import make_password


YEAR_CHOICES = [(r,r) for r in range(1900, datetime.date.today().year+1)]


# Урочище ссылается на квартал.

class Table(models.Model):
    name = models.CharField(max_length=300)
    age = models.IntegerField()

    class Meta:
        verbose_name = "Таблица"
        verbose_name_plural = "Таблицы"


class Undergrowth(models.Model):
    name = models.CharField(max_length=350, verbose_name='Наименовапние')

    class Meta:
        verbose_name = 'Подлесок'

class UndergrowthByDefault(models.Model):
    id_profile = models.ForeignKey('Profile', on_delete=models.CASCADE, verbose_name='Профиль')
    id_undergrowth = models.ForeignKey('Undergrowth', on_delete=models.CASCADE, verbose_name='Подлесок')

    class Meta:
        verbose_name = 'Подлесок по умолчанию'
        verbose_name_plural = 'Подлесок по умолчанию'

# в регистрацию добавить субъект
class Users(AbstractBaseUser, PermissionsMixin):
    email = models.EmailField(unique=True)
    password = models.CharField(max_length=128)
    is_active = models.BooleanField(default=True)
    subject_rf = models.ForeignKey("SubjectRF", on_delete=models.CASCADE, null = True)
    is_staff = None
    is_admin = None
    is_superuser = None
    last_login = None

    USERNAME_FIELD = 'email'
    objects = AccountManager()

    # При сохранении хэшируем пароль
    def save(self, *args, **kwargs):
        self.password = make_password(self.password)
        super(Users, self).save(*args, **kwargs)

    def has_perm(self, perm, obj=None):
        return True

    def has_module_perms(self, app_label):
        return True

    def __unicode__(self):
        return self.login


    class Meta:
        verbose_name = 'Пользователи'
        verbose_name_plural = 'Пользователи'


class CheckTrigger(models.Model):
    bool = models.IntegerField(default=False)
    

class Track(models.Model):
    id_profile = models.ForeignKey('Profile', on_delete=models.CASCADE, verbose_name='Профиль')
    data = models.DateField()
    map = models.CharField(max_length=1)

    class Meta:
        verbose_name = 'Трекинг'
        verbose_name_plural = 'Трекинг'

class PhotoPoint(models.Model):
    photo = models.ImageField(upload_to="photos/%Y/%m/%d/")
    id_sample = models.ForeignKey("Sample", on_delete=models.CASCADE, verbose_name='Проба', null = True)
    latitude = models.FloatField(u'Широта', blank=True, default=0)
    longitude = models.FloatField(u'Долгота', blank=True, default=0)
    date = models.CharField(u'Дата', null=True, max_length=100)

    class Meta:
        verbose_name = 'Фото точка'
        verbose_name_plural = 'Фото точка'

class Profile(models.Model):
    FIO = models.CharField(u'ФИО', max_length=255)
    id_user = models.ForeignKey('Users', on_delete=models.CASCADE, verbose_name='Пользователь', null=True)
    phoneNumber = models.CharField(u'Номер телефона', max_length=30)
    id_post = models.ForeignKey("Post", on_delete=models.CASCADE, verbose_name='Должность', null=True)
    id_role = models.ForeignKey('Role', on_delete=models.CASCADE, verbose_name='Роль', null=True)
    id_branches = models.ForeignKey('Branches', on_delete=models.CASCADE, verbose_name='Филиал', null=True)


    class Meta:
        verbose_name = 'Профили'
        verbose_name_plural = 'Профили'

    def __str__(self):
        return self.FIO


class List(models.Model):
    id_sample = models.ForeignKey('Sample', on_delete=models.CASCADE, verbose_name='Проба', null=True)
    id_breed = models.ForeignKey('Breed', on_delete=models.CASCADE, verbose_name='Порода', null=True)
    id_type_of_reproduction = models.ForeignKey('Reproduction', on_delete=models.CASCADE,
                                                verbose_name='Вид воспроизводства', null=True)
    id_undergrowth = models.ForeignKey('Undergrowth', on_delete= models.CASCADE, verbose_name='Подлесок'
                                       , null= True)
    to0_2 = models.IntegerField(u'До 0,2', null=True)
    from0_21To0_5 = models.IntegerField(u'0,21 - 0,5', null=True)
    from0_6To1_0 = models.IntegerField(u'0,6 - 1,0', null=True)
    from1_1to1_5 = models.IntegerField(u'1,1 - 1,5', null=True)
    from1_5 = models.IntegerField(u'более 1,5', null=True)
    max_height = models.FloatField(u'Максимальная высота', null=True, default=0)
    avg_diameter = models.FloatField(u'Средний диаметр', null=True, default=0)
    count_of_plants = models.IntegerField(u'Количество растений для подлеска', null = True)
    avg_height = models.FloatField(u'Средняя высота', null=True, default=0)
    avg_height_undergrowth = models.FloatField(u'Средняя высота подлеска', null = True)
    main = models.IntegerField(null=True, default=0)
    mark_update = models.IntegerField(null=True, default=0)
    age = models.IntegerField(verbose_name="Возраст", null=True)
    ratio_composition = models.IntegerField(verbose_name="Коэфф. состава", null=True)


    class Meta:
        verbose_name = 'Перечет'
        verbose_name_plural = 'Перечет'

    def __str__(self):
        return f'{self.to0_2}, {self.from0_21To0_5},\n {self.from0_6To1_0}, {self.from1_1to1_5}, {self.from1_5}'

    # def save(self, *args, **kwargs):
    #     self.ratio_composition = self.count_of_plants


class GPS(models.Model):
    id_sample = models.ForeignKey('Sample', on_delete=models.CASCADE, verbose_name='Проба', null=True)
    latitude = models.FloatField(u'Широта')
    longitude = models.FloatField(u'Долгота')
    flag_center = models.IntegerField(u'Флаг центра')

    class Meta:
        verbose_name = 'GPS'
        verbose_name_plural = 'GPS'

    def __str__(self):
        return f"{self.latitude}, {self.longitude} "


class ListRegion(models.Model):
    date = models.DateField(u'Дата')
    sample_region = models.FloatField(u'Плошадь участка, га', max_length=300)
    id_quarter = models.ForeignKey("Quarter", on_delete=models.CASCADE, verbose_name="Квартал", null=True)
    soil_lot = models.CharField(max_length=300, verbose_name='Выдел', default=0, null=True)
    mark_del = models.IntegerField(null = True)
    mark_update = models.IntegerField(null= True)
    number_region = models.CharField(max_length=100, default=0)
    id_profile = models.ForeignKey("Profile", on_delete=models.CASCADE, null=True)

    class Meta:
        verbose_name = 'Перечетная ведомость участка'
        verbose_name_plural = 'Перечетная ведомость участка'

    def __str__(self):
        # return f"{self.date}, {self.soil_lot}"
        return f"{self.id}"


class Sample(models.Model):
    date = models.DateField(u'Дата пробы', null=True)
    sample_area = models.FloatField(verbose_name='Площадь пробы, га')
    id_profile = models.ForeignKey('Profile', on_delete=models.CASCADE, verbose_name='Сотрудник ', null=True)
    id_list_region = models.ForeignKey('ListRegion', on_delete=models.CASCADE,
                                       verbose_name='Перечетная ведомость участка', null=True)
    id_quarter = models.ForeignKey('Quarter', on_delete=models.CASCADE,
                                   verbose_name='Квартал', null=True)
    soil_lot = models.CharField(max_length=300, verbose_name='Выдел', null=True, default=0)
    width = models.FloatField(u'Ширина', null=True, default=0)
    lenght = models.FloatField(u'Длина', null=True, default=0)
    square = models.FloatField(u'Площадь', null=True, default=0)
    mark_update = models.IntegerField(null=True, default=0)


    class Meta:
        verbose_name = 'Проба'
        verbose_name_plural = 'Проба'

    def __str__(self):
        return f"({self.id})-{self.date}, {self.sample_area}, {self.soil_lot}"


class Post(models.Model):
    post_name = models.CharField(max_length=255, verbose_name='Должность')

    def __str__(self):
        return self.post_name

    class Meta:
        verbose_name = 'Должности'
        verbose_name_plural = 'Должности'


class WorkingBreeds(models.Model):
    name_breeds = models.CharField(max_length=255, verbose_name='Наименование рабочей породы')

    def __str__(self):
        return self.name_breeds

    class Meta:
        verbose_name = 'Рабочие породы'
        verbose_name_plural = 'Рабочие породы'


class SubjectRF(models.Model):
    name_subject_RF = models.CharField(max_length=255, verbose_name='Наименования субъекта РФ')

    def __str__(self):
        return f"{self.name_subject_RF}"

    class Meta:
        verbose_name = 'Субъект РФ'
        verbose_name_plural = 'Субъект РФ'


class Role(models.Model):
    name_role = models.CharField(max_length=300, verbose_name='Наименование')

    def __str__(self):
        return self.name_role

    class Meta:
        verbose_name = 'Роли'
        verbose_name_plural = 'Роли'


class Reproduction(models.Model):
    name_reproduction = models.CharField(max_length=500, verbose_name='Наименование')

    def __str__(self):
        return self.name_reproduction

    class Meta:
        verbose_name = 'Вид воспроизводства'
        verbose_name_plural = 'Вид воспроизводства'


class Forestly(models.Model):
    name_forestly = models.CharField(max_length=500, verbose_name='Название лесничества')
    id_subject_rf = models.ForeignKey('SubjectRF', on_delete=models.CASCADE, verbose_name="Субъект РФ", null=True)

    def __str__(self):
        return f"{self.name_forestly}"

    class Meta:
        verbose_name = 'Лесничество'
        verbose_name_plural = 'Лесничество'


class DistrictForestly(models.Model):
    name_district_forestly = models.CharField(max_length=500, verbose_name='Наименование участкового лесничества')
    id_forestly = models.ForeignKey('Forestly', on_delete=models.CASCADE, verbose_name='Лесничество', null=True)

    def __str__(self):
        return self.name_district_forestly

    class Meta:
        verbose_name = 'Участковое лесничество'
        verbose_name_plural = 'Участковое лесничество'


class Quarter(models.Model):
    quarter_name = models.CharField(u'Наименование', max_length=50)
    id_district_forestly = models.ForeignKey("DistrictForestly", on_delete=models.CASCADE, verbose_name="Участковое лесничество", null=True)

    class Meta:
        verbose_name = 'Квартал'
        verbose_name_plural = 'Квартал'

    def __str__(self):
        return self.quarter_name


class ForestFormingByDefault(models.Model):
    id_profile = models.ForeignKey('Profile', on_delete = models.CASCADE, verbose_name='Профиль')
    id_breed = models.ForeignKey('Breed', on_delete= models.CASCADE, verbose_name='Порода')

    def save(self, *args,  **kwargs):
        if ForestFormingByDefault.objects.get(id_profile_id = self.id_profile, id_breed_id = self.id_breed):
            return self
        super(ForestFormingByDefault, self).save(*args, **kwargs)

    class Meta:
        verbose_name = "Лесообразующие породы по умолчанию"


class GenericNameBreed(models.Model):
    name_generic = models.CharField(max_length = 150, verbose_name = 'Родовое название породы')

    class Meta:
        verbose_name = 'Родовое название породы'
        verbose_name_plural = 'Родовые названия пород'


class LifeForm(models.Model):
    name_life = models.CharField(max_length=100, verbose_name="Наименование жизненной формы")

    class Meta:
        verbose_name = 'Жизненная форма'
        verbose_name_plural = 'Жизненные формы'


class Breed(models.Model):
    name_breed = models.CharField(max_length=350, verbose_name='Наименование породы')
    is_pine = models.BooleanField(null=True, default=0, verbose_name="Хвойное")
    is_foliar = models.BooleanField(null=True, default=0, verbose_name="Лиственное")
    short_name = models.CharField(max_length=50, verbose_name='Сокр.', null = True)
    latin_name = models.CharField(max_length=300, verbose_name='Латинское наименование', null=True)
    life_form = models.ForeignKey('LifeForm', on_delete=models.CASCADE, verbose_name='Жизненна форма', null=True)
    generic_name = models.ForeignKey('GenericNameBreed', on_delete=models.CASCADE,
                                     verbose_name='Родовое название породы',
                                     null=True)
    economy = models.ForeignKey('Economy', on_delete=models.CASCADE,
                                verbose_name='Хозяйство', null=True)
    evergreen = models.IntegerField(verbose_name= 'Вечнозеленость', null=True)
    def __str__(self):
        return self.name_breed

    class Meta:
        verbose_name = 'Лесообразующие породы'
        verbose_name_plural = 'Лесообразующие породы'


class Branches(models.Model):
    name_branch = models.CharField(max_length=350, verbose_name='Наименование филиала')

    def __str__(self):
        return self.name_branch

    class Meta:
        verbose_name = 'Филиал'
        verbose_name_plural = 'Филиал'


class PurposeOfForests(models.Model):
    name_purpose = models.CharField(max_length=350, verbose_name='Наименование')

    def __str__(self):
        return self.name_purpose

    class Meta:
        verbose_name = 'Целевое назначение лесов'
        verbose_name_plural = 'Целевое назначение лесов'


class ForestProtectionCategory(models.Model):
    name_forest_protection_category = models.CharField(max_length=350, verbose_name='Наименование')

    def __str__(self):
        return self.name_forest_protection_category

    class Meta:
        verbose_name = 'Категория защитности лесов'
        verbose_name_plural = 'Категория защитности лесов'


class CategoryOfForestFundLands(models.Model):
    name_category = models.CharField(max_length=350,  verbose_name='Наименование')

    def __str__(self):
        return self.name_category

    class Meta:
        verbose_name = 'Категория земель лесного фонда'
        verbose_name_plural = 'Категория земель лесного фонда'


class MethodOfReforestation(models.Model):
    name_of_method = models.CharField(max_length=350, verbose_name='Наименование')

    def __str__(self):
        return self.name_of_method

    class Meta:
        verbose_name = 'Способ лесовосстановления'
        verbose_name_plural = 'Способ лесовосстановления'


class BonitetOrlov(models.Model):
    age_of_planting = models.FloatField(verbose_name='Возраст насаждения')
    height_planting_for_bonitet_class = models.CharField(max_length=300, verbose_name='Высота насаждения по классам бонитета, м')
    class_bonitet = models.CharField(max_length=300, verbose_name='Класс бонитета')

    def __str__(self):
        return self.class_bonitet

    class Meta:
        verbose_name = 'Бонитет по Орлову'
        verbose_name_plural = 'Бонитет по Орлову'


class TypeForestGrowingConditions(models.Model):
    subtypes_of_humidity = models.CharField(max_length=300, verbose_name='Подтипы влажности')
    subtypes_of_rich = models.CharField(max_length=300, verbose_name='Подтипы богатства')
    type_forest_growing_conditions = models.CharField(max_length=300, verbose_name='Тип лесорастительный условий')

    def __str__(self):
        return self.type_forest_growing_conditions

    class Meta:
        verbose_name = 'Тип лесорастительный условий'
        verbose_name_plural = 'Тип лесорастительный условий'


class Economy(models.Model):
    name_economy = models.CharField(max_length=300, verbose_name='Наименование')

    def __str__(self):
        return self.name_economy

    class Meta:
        verbose_name = 'Хозяйство'
        verbose_name_plural = 'Хозяйство'


class AccordanceMolodKrAndTPPL(models.Model):
    name_of_accordance = models.CharField(max_length=300, verbose_name='Наименование')

    def __str__(self):
        return self.name_of_accordance

    class Meta:
        verbose_name = 'Соответ. молодняка кр. и тр. ПЛ'
        verbose_name_plural = 'Соответ. молодняка кр. и тр. ПЛ'


class AccordanceNoneAccordanceEconomy(models.Model):
    name_accordance_none_economy = models.CharField(max_length=300, verbose_name='Наименование')

    def __str__(self):
        return self.name_accordance_none_economy

    class Meta:
        verbose_name = 'Соответ. не соответ. хозяйству'
        verbose_name_plural = 'Соответ. не соответ. хозяйству '


class CategoryGroundLFInNoneAccordance(models.Model):
    name_of_category_ground = models.CharField(max_length=300, verbose_name='Наименование')

    def __str__(self):
        return self.name_of_category_ground

    class Meta:
        verbose_name = 'Кат. земель лф в случ. несоотв.'
        verbose_name_plural = 'Кат. земель лф в случ. несоотв.'


class ForestAreas(models.Model):
    name_forest_areas = models.CharField(max_length=300, verbose_name='Наименование')
    composition_of_forest_areas = models.CharField(max_length=500, verbose_name='Состав лесных районов')
    comm = models.CharField(max_length=500, verbose_name='Комментарий')

    def __str__(self):
        return self.name_forest_areas

    class Meta:
        verbose_name = 'Лесные районы'
        verbose_name_plural = 'Лесные районы'


class point7Table(models.Model):
    ratio_composition = models.CharField(max_length=100)
    id_list_region_breed = models.ForeignKey("List", on_delete=models.CASCADE, verbose_name="Порода", default=89)
    age = models.IntegerField()
    avg_height = models.IntegerField()
    avg_diameter = models.IntegerField()
    count_register_wood_plants = models.IntegerField()


class point7Table2Sapling(models.Model):
    ratio_composition = models.CharField(max_length=100)
    id_list_region_breed = models.ForeignKey("List", on_delete=models.CASCADE, verbose_name="Порода", default=89)
    age = models.IntegerField()
    avg_height = models.IntegerField()
    avg_diameter = models.IntegerField()
    count_register_wood_plants = models.IntegerField()


class DescriptionRegion(models.Model):
    id_list_region = models.ForeignKey("ListRegion",
                                       on_delete=models.CASCADE,
                                       verbose_name="Перечетная ведомость участка", null=True)
    # MethodOfReforestation
    id_method_of_reforestation = models.ForeignKey("MethodOfReforestation",
                                                   on_delete=models.CASCADE, verbose_name="Способ лесовосстановления",
                                                   null=True)
    year_assignment_land = models.IntegerField(choices=YEAR_CHOICES, default=datetime.datetime.now().year,
                                            verbose_name="Год отнесения к землям", null=True)
    year_format_fond_trees = models.IntegerField(choices=YEAR_CHOICES, default=datetime.datetime.now().year,
                                              verbose_name="Год образования категории фонда лесовосстановления", null=True)
    inf_restore_forest = models.CharField(max_length=500,
                                          verbose_name="Данные о проведенных мероприятиях по уходу за лесами", null=True)
    breed_structure_sapling_act_land = models.CharField(max_length=500,
                                                        verbose_name="Породный состав молодняка по Акту отнесения земель", null=True)
    economy_act_land = models.CharField(max_length=500,
                                        verbose_name="Хозяйство по Акту отнесения земель")
    change_breed_and_structure_sapling = models.CharField(max_length=500, null=True,
                                                          verbose_name="Изменение породного и качественного состава молодняка")
    results_surtvey = models.CharField(max_length=600, null=True, verbose_name="Вывод по результатам обследования")
    recommendation = models.CharField(max_length=300, null=True, verbose_name="Рекомендации")
    id_schema_mixing_breeds = models.ForeignKey("SchemaMixingBreeds", on_delete=models.CASCADE,
                                                null=True, verbose_name="Схема смешения пород")
    count_plants = models.FloatField(verbose_name="Количество высаженных растений на 1 га",
                                     null=True)
    preservation_breed = models.CharField(max_length=300,
                                          verbose_name="сохранность культивируемой породы на момент обследования",
                                          null=True)
    farm_according_data_survey = models.CharField(max_length=300,
                                                  verbose_name="Хозяйство по данным натурного обследования ",
                                                  null=True)
    breed_composition_sapling_data_surver = models.CharField(max_length=300,
                                                             verbose_name="Породный состав молодняка по данным натурного обследования",
                                                             null=True)


class FieldCard(models.Model):
    id_list_region = models.ForeignKey("ListRegion",
                                       on_delete=models.CASCADE,
                                       verbose_name="Перечетная ведомость участка", null=True)
    id_purpose_of_forests = models.ForeignKey("PurposeOfForests",
                                              on_delete=models.CASCADE,
                                              verbose_name="Целевое назначение лесов", null=True)
    id_forest_protection_category = models.ForeignKey("ForestProtectionCategory",
                                                      on_delete=models.CASCADE,
                                                      verbose_name="Категория защитных лесов", null=True)
    protected_areas_of_forests = models.CharField(max_length=300, verbose_name="Особо защитные участки лесов", null=True)
    rent_area = models.BooleanField(default=0, verbose_name="Участок находится в аренде", null=True)
    id_category_of_forest_fund_lands = models.ForeignKey("CategoryOfForestFundLands",
                                                         on_delete=models.CASCADE,
                                                         verbose_name="Категория земель лесного фонда", null=True)
    id_method_of_reforestation = models.ForeignKey("MethodOfReforestation",
                                                   on_delete=models.CASCADE,
                                                   verbose_name="Способ лесовосстановления", null=True)
    time_of_reforestation = models.CharField(max_length=300, verbose_name="Срок проведения лесовосстановления", null=True)
    id_type_forest_growing_conditions = models.ForeignKey("TypeForestGrowingConditions",
                                                          on_delete=models.CASCADE,
                                                          verbose_name="Тип лесорастительных условий", null=True)
    forest_type = models.CharField(max_length=100, null=True)
    point7year = models.IntegerField(choices=YEAR_CHOICES, default=datetime.datetime.now().year,
                                     verbose_name="Год", null=True)
    point7date = models.DateField(verbose_name="Дата", null=True)
    point7number = models.IntegerField(verbose_name="Номер", null=True)
    point7agreed = models.CharField(max_length=300, verbose_name="Утверждено", null=True)
    point7_natural_composition = models.CharField(max_length=300, verbose_name="Природный состав", null=True)
    id_economy = models.CharField(max_length=300, verbose_name="Природный состав 2", null=True)
    point7_completeness = models.CharField(max_length=300, verbose_name="Полнота", null=True)
    point7_stock = models.IntegerField(verbose_name="Запас", null=True)
    id_point7table = models.ForeignKey("point7Table", on_delete=models.CASCADE, default=1, null=True)
    square_one_sample_area = models.FloatField(verbose_name="Площадь 1 пробной площади", null=True)
    count_sample_area = models.IntegerField(verbose_name="Количество пробных площадей", null=True)
    breed_composition = models.CharField(max_length=500, verbose_name="Породный состав", null=True)
    id_economy_sapling = models.ForeignKey("Economy",
                                   on_delete=models.CASCADE,
                                   verbose_name="Хозяйство", null=True)
    completeness = models.CharField(max_length=300, verbose_name="Полнота", null=True)
    stock = models.IntegerField(verbose_name="Запас", null=True)
    id_point7_table2_sapling = models.ForeignKey("point7Table2Sapling",
                                                 on_delete=models.CASCADE,
                                                 verbose_name="Таблица 2 молодняк", null=True)
    conclusion = models.CharField(max_length=500, verbose_name="Заключение", null=True)
    plot_farm_referring_land = models.CharField(max_length=500, verbose_name="Участок хозяйству при отнесении к землям", null=True)
    recomendation = models.CharField(max_length=300, verbose_name="Рекомендации", null=True)
    plot_features = models.CharField(max_length=500, verbose_name="Особенности участка", null=True)
    site_survey = models.CharField(max_length=300, verbose_name="Обследование провел", null=True)
    in_front = models.CharField(max_length=300, verbose_name="В присуствии", null=True)
    date_and_time = models.DateTimeField(verbose_name="Дата и время обследования", null=True)
    number_order = models.IntegerField(u"Номер приказа")
    lands_other = models.CharField(max_length=200, null=True)


    class Meta:
        verbose_name = "Полевая карточка"
        verbose_name_plural = "Полевая карточка"


class SchemaMixingBreeds(models.Model):
    name_schema = models.CharField(max_length=300)


class PlotCoeff(models.Model):
    ratio_composition = models.CharField(max_length=100)
    breed = models.ForeignKey("Breed", on_delete=models.CASCADE)
    age = models.IntegerField()
    avg_diameter = models.FloatField()
    avg_height = models.FloatField()
    count_plants = models.IntegerField()
    id_field_card = models.ForeignKey("FieldCard", on_delete=models.CASCADE)

    class Meta:
        verbose_name = "Коэфф. участка"
        verbose_name_plural = "Коэфф. участка"


class FederalDistricts(models.Model):
    name_federal = models.CharField(max_length=250)

    class Meta:
        verbose_name = "Федеральные округа"
        verbose_name_plural = 'Федеральные округа'

    def __str__(self):
        return self.name_federal


class MethodForestRestoration(models.Model):
    name_method = models.CharField(max_length=600)


    class Meta:
        verbose_name = "Метод лесовосстановления"
        verbose_name_plural = "Метод лесовосстановления"

    def __str__(self):
        return self.name_method

class ForestCareActivities(models.Model):
    name_activities = models.CharField(max_length=100)


    class Meta:
        verbose_name = "Мероприятия по уходу за лесами"
        verbose_name_plural = "Мероприятия по уходу за лесами"

    def __str__(self):
        return self.name_activities


class Recommendation(models.Model):
    name_recommendation = models.CharField(max_length=100)

    class Meta:
        verbose_name = "Рекомендации по результатам натурных обследований"
        verbose_name_plural = "Рекомендации по результатам натурных обследований"

    def __str__(self):
        return self.name_recommendation


class ForestDistricts(models.Model):
    name_forest_district = models.CharField(max_length=300)

    class Meta:
        verbose_name = "Лесные районы РФ"
        verbose_name_plural = "Лесные районы РФ"

    def __str__(self):
        return self.name_forest_district