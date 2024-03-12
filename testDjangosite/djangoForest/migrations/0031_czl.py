# Generated by Django 4.1 on 2024-03-12 12:49

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('djangoForest', '0030_fieldcard_respond_farm'),
    ]

    operations = [
        migrations.CreateModel(
            name='CZL',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name_czl', models.CharField(max_length=300, verbose_name='Наименование')),
                ('id_main_subject', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='djangoForest.subjectrf', verbose_name='Главный субъект')),
                ('id_subject', models.ManyToManyField(blank=True, related_name='slave_subject', to='djangoForest.subjectrf', verbose_name='Подчиненный субъект')),
            ],
        ),
    ]
