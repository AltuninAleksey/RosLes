# Generated by Django 4.1 on 2024-02-20 11:27

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('djangoForest', '0025_remove_listregion_id_quarter_listregion_dacha_and_more'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='listregion',
            name='id_district_forestly',
        ),
    ]
