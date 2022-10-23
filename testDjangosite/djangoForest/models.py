from django.db import models


class Table(models.Model):
    name = models.CharField(max_length=300)
    age = models.IntegerField()

    class Meta:
        verbose_name = "Таблица"
        verbose_name_plural = "Таблицы"


class Profile(models.Model):
    FIO = models.CharField(u'ФИО', max_length=255)
    phoneNumber = models.CharField(u'Номер телефона', max_length=30)
    email = models.EmailField(u'e-mail адрес')
    id_post = models.ForeignKey('Post', on_delete=models.CASCADE, verbose_name='Должность')
    id_working_breeds = models.ForeignKey('WorkingBreeds', on_delete=models.CASCADE, verbose_name='Рабочая порода')
    id_role = models.ForeignKey('Role', on_delete=models.CASCADE, verbose_name='Роль')
    id_branches = models.ForeignKey('Branches', on_delete=models.CASCADE, verbose_name='Филиал')

    class Meta:
        verbose_name = 'Профили'
        verbose_name_plural = 'Профили'


class List(models.Model):
    id_sample = models.ForeignKey('Sample', on_delete=models.CASCADE, verbose_name='Перечет')
    id_breed = models.ForeignKey('Breed', on_delete=models.CASCADE, verbose_name='Порода')
    id_type_of_reproduction = models.ForeignKey('Reproduction', on_delete=models.CASCADE,
                                                verbose_name='Вид воспроизводства')
    to0_2 = models.IntegerField(u'До 0,2')
    from0_21To0_5 = models.IntegerField(u'0,21 - 0,5')
    from0_6To1_0 = models.IntegerField(u'0,6 - 1,0')
    from1_1to1_5 = models.IntegerField(u'1,1 - 1,5')
    from1_5 = models.IntegerField(u'более 1,5')
    max_height = models.IntegerField(u'Максимальная высота')

    class Meta:
        verbose_name = 'Перечет'
        verbose_name_plural = 'Перечет'


class GPS(models.Model):
    id_sample = models.ForeignKey('Sample', on_delete=models.CASCADE, verbose_name='Проба')
    latitude = models.FloatField(u'Широта')
    longitude = models.FloatField(u'Долгота')
    flag_center = models.IntegerField(u'Флаг центра')

    class Meta:
        verbose_name = 'GPS'
        verbose_name_plural = 'GPS'


class Region(models.Model):
    id_subject_rf = models.ForeignKey('SubjectRF', on_delete=models.CASCADE, verbose_name='Субъект РФ')
    id_forestly = models.ForeignKey('Forestly', on_delete=models.CASCADE, verbose_name='Лесничество')
    id_district_forestly = models.ForeignKey('DistrictForestly', on_delete=models.CASCADE,
                                             verbose_name='Участковое лесничество')
    quarter = models.IntegerField(u'Квартал')
    soil_lot = models.IntegerField(u'Выдел')
    sample_region = models.IntegerField(u'Площадь участка')

    class Meta:
        verbose_name = 'Участок'
        verbose_name_plural = 'Участок'


class ListRegion(models.Model):
    id_region = models.ForeignKey('Region', on_delete=models.CASCADE, verbose_name='Участок')
    date = models.DateField(u'Дата')
    sample_region = models.CharField(u'Плошадь участка', max_length=300)
    id_subject_RF = models.ForeignKey('SubjectRF', on_delete=models.CASCADE, verbose_name='Субъект РФ')
    id_forestly = models.ForeignKey('Forestly', on_delete=models.CASCADE, verbose_name='Лесничество')
    id_district_forestly = models.ForeignKey('DistrictForestly', on_delete=models.CASCADE,
                                             verbose_name='Участковое лесничество')

    class Meta:
        verbose_name = 'Перечетная ведомость участка'
        verbose_name_plural = 'Перечетная ведомость участка'


class Sample(models.Model):
    date = models.DateField(u'Дата', null=True)
    sample_area = models.IntegerField(u'Площадь пробы')
    id_profile = models.ForeignKey('Profile', on_delete=models.CASCADE, verbose_name='Профиль')
    id_subject_RF = models.ForeignKey('SubjectRF', on_delete=models.CASCADE, verbose_name='Субъект РФ')
    id_forestly = models.ForeignKey('Forestly', on_delete=models.CASCADE, verbose_name='Лесничество')
    id_district_forestly = models.ForeignKey('DistrictForestly', on_delete=models.CASCADE,
                                             verbose_name='Участковое лесничество')
    soil_lot = models.IntegerField(u'Выдел')
    id_list_region = models.ForeignKey('ListRegion', on_delete=models.CASCADE,
                                       verbose_name='Перечетная ведомость участка')

    class Meta:
        verbose_name = 'Проба'
        verbose_name_plural = 'Проба'


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
        return self.name_subject_RF

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

    def __str__(self):
        return self.name_forestly

    class Meta:
        verbose_name = 'Лесничество'
        verbose_name_plural = 'Лесничество'


class DistrictForestly(models.Model):
    name_district_forestly = models.CharField(max_length=500, verbose_name='Наименование участкового лесничества')

    def __str__(self):
        return self.name_district_forestly

    class Meta:
        verbose_name = 'Участковое лесничество'
        verbose_name_plural = 'Участковое лесничество'


class Breed(models.Model):
    name_breed = models.CharField(max_length=350, verbose_name='Наименование породы')

    def __str__(self):
        return self.name_breed

    class Meta:
        verbose_name = 'Порода'
        verbose_name_plural = 'Порода'


class Branches(models.Model):
    name_branch = models.CharField(max_length=350, verbose_name='Наименование филиала')
    id_subject_RF = models.ForeignKey('SubjectRF', on_delete=models.CASCADE, verbose_name='Субъект РФ')

    def __str__(self):
        return self.name_branch

    class Meta:
        verbose_name = 'Филиал'
        verbose_name_plural = 'Филиал'