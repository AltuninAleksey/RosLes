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
    id_post = models.ForeignKey("Post", on_delete=models.CASCADE, verbose_name='Должность', null=True)
    id_working_breeds = models.ForeignKey('WorkingBreeds', on_delete=models.CASCADE, verbose_name='Рабочая порода', null=True)
    id_role = models.ForeignKey('Role', on_delete=models.CASCADE, verbose_name='Роль', null=True)
    id_branches = models.ForeignKey('Branches', on_delete=models.CASCADE, verbose_name='Филиал', null=True)

    class Meta:
        verbose_name = 'Профили'
        verbose_name_plural = 'Профили'


class List(models.Model):
    id_sample = models.ForeignKey('Sample', on_delete=models.CASCADE, verbose_name='Перечет', null=True)
    id_breed = models.ForeignKey('Breed', on_delete=models.CASCADE, verbose_name='Порода', null=True)
    id_type_of_reproduction = models.ForeignKey('Reproduction', on_delete=models.CASCADE,
                                                verbose_name='Вид воспроизводства', null=True)
    to0_2 = models.IntegerField(u'До 0,2', null=True)
    from0_21To0_5 = models.IntegerField(u'0,21 - 0,5', null=True)
    from0_6To1_0 = models.IntegerField(u'0,6 - 1,0', null=True)
    from1_1to1_5 = models.IntegerField(u'1,1 - 1,5', null=True)
    from1_5 = models.IntegerField(u'более 1,5', null=True)
    max_height = models.IntegerField(u'Максимальная высота', null=True)

    class Meta:
        verbose_name = 'Перечет'
        verbose_name_plural = 'Перечет'

    def __str__(self):
        return f'{self.to0_2}'


class GPS(models.Model):
    id_sample = models.ForeignKey('Sample', on_delete=models.CASCADE, verbose_name='Проба', null=True)
    latitude = models.FloatField(u'Широта')
    longitude = models.FloatField(u'Долгота')
    flag_center = models.IntegerField(u'Флаг центра')

    class Meta:
        verbose_name = 'GPS'
        verbose_name_plural = 'GPS'


# class Region(models.Model):
#     id_subject_rf = models.ForeignKey('SubjectRF', on_delete=models.CASCADE, verbose_name='Субъект РФ', null = True)
#     id_forestly = models.ForeignKey('Forestly', on_delete=models.CASCADE, verbose_name='Лесничество', null = True)
#     id_district_forestly = models.ForeignKey('DistrictForestly', on_delete=models.CASCADE,
#                                              verbose_name='Участковое лесничество', null=True)
#     quarter = models.IntegerField(u'Квартал')
#     soil_lot = models.IntegerField(u'Выдел')
#     sample_region = models.IntegerField(u'Площадь участка')
#
#     class Meta:
#         verbose_name = 'Участок'
#         verbose_name_plural = 'Участок'


class ListRegion(models.Model):
    date = models.DateField(u'Дата')
    sample_region = models.CharField(u'Плошадь участка', max_length=300)
    id_quarter = models.ForeignKey("Quarter", on_delete=models.CASCADE, verbose_name="Квартал", null=True)
    soil_lot = models.CharField(u'Выдел', max_length=300)

    class Meta:
        verbose_name = 'Перечетная ведомость участка'
        verbose_name_plural = 'Перечетная ведомость участка'


class Sample(models.Model):
    date = models.DateField(u'Дата пробы', null=True)
    sample_area = models.IntegerField(u'Площадь пробы')
    id_profile = models.ForeignKey('Profile', on_delete=models.CASCADE, verbose_name='Сотрудник ', null=True)
    id_list_region = models.ForeignKey('ListRegion', on_delete=models.CASCADE,
                                       verbose_name='Перечетная ведомость участка', null=True)

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
    id_subject_rf = models.ForeignKey('SubjectRF', on_delete=models.CASCADE, verbose_name="Субъект РФ", null=True)

    def __str__(self):
        return self.name_forestly

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
    id_forestly = models.ForeignKey("Forestly", on_delete=models.CASCADE, verbose_name="Лесничество", null=True)

    class Meta:
        verbose_name = 'Квартал'
        verbose_name_plural = 'Квартал'


class Breed(models.Model):
    name_breed = models.CharField(max_length=350, verbose_name='Наименование породы')
    id_forestly = models.ForeignKey('Forestly', on_delete=models.CASCADE, verbose_name="Лесничество", null=True)

    def __str__(self):
        return self.name_breed

    class Meta:
        verbose_name = 'Порода'
        verbose_name_plural = 'Порода'


class Branches(models.Model):
    name_branch = models.CharField(max_length=350, verbose_name='Наименование филиала')

    def __str__(self):
        return self.name_branch

    class Meta:
        verbose_name = 'Филиал'
        verbose_name_plural = 'Филиал'