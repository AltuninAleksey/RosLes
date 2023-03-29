from django.db import models



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

class Users(models.Model):
    email = models.EmailField(unique=True)
    password = models.CharField(max_length=300)

    def __str__(self):
        return self.id


class CheckTrigger(models.Model):
    bool = models.BooleanField(default=False)
    

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

    class Meta:
        verbose_name = 'Фото точка'
        verbose_name_plural = 'Фото точка'

class Profile(models.Model):
    FIO = models.CharField(u'ФИО', max_length=255)
    id_user = models.ForeignKey('Users', on_delete=models.CASCADE, verbose_name='Пользователь', null=True)
    phoneNumber = models.CharField(u'Номер телефона', max_length=30)
    # password = models.CharField(u'Пароль', max_length=100)
    id_post = models.ForeignKey("Post", on_delete=models.CASCADE, verbose_name='Должность', null=True)
    id_role = models.ForeignKey('Role', on_delete=models.CASCADE, verbose_name='Роль', null=True)
    id_branches = models.ForeignKey('Branches', on_delete=models.CASCADE, verbose_name='Филиал', null=True)

    def __str__(self):
        return self.FIO

    class Meta:
        verbose_name = 'Профили'
        verbose_name_plural = 'Профили'


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
    max_height = models.FloatField(u'Максимальная высота', null=True)
    avg_diameter = models.FloatField(u'Средний диаметр', null=True)
    count_of_plants = models.IntegerField(u'Количество растений для подлеска', null = True)
    avg_height = models.FloatField(u'Средняя высота', null=True)
    avg_height_undergrowth = models.FloatField(u'Средняя высота подлеска', null = True)
    main = models.BooleanField(null=True, default=0)

    class Meta:
        verbose_name = 'Перечет'
        verbose_name_plural = 'Перечет'

    def __str__(self):
        return f'{self.to0_2}, {self.from0_21To0_5},\n {self.from0_6To1_0}, {self.from1_1to1_5}, {self.from1_5}'


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
    soil_lot = models.CharField(max_length=300, verbose_name='Выдел')
    mark_del = models.BooleanField(null = True)

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
    soil_lot = models.CharField(max_length=300, verbose_name='Выдел', null=True)
    width = models.FloatField(u'Ширина', null=True)
    lenght = models.FloatField(u'Длина', null=True)
    square = models.FloatField(u'Площадь', null=True)


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


class Breed(models.Model):
    name_breed = models.CharField(max_length=350, verbose_name='Наименование породы')
    is_pine = models.BooleanField(null=True, default=0, verbose_name="Хвойное")
    is_foliar = models.BooleanField(null=True, default=0, verbose_name="Лиственное")
    ShortName = models.CharField(max_length=10, verbose_name='Сокр.', null = True)

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

