# Generated by Django 4.1.3 on 2023-05-08 13:52

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('djangoForest', '0043_listregion_number_region'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='point7table',
            name='breed',
        ),
        migrations.AddField(
            model_name='point7table',
            name='id_breed',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.CASCADE, to='djangoForest.breed', verbose_name='Порода'),
        ),
    ]
