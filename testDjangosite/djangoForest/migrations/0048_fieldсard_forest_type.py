# Generated by Django 4.1.3 on 2023-05-09 08:48

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('djangoForest', '0047_remove_point7table_id_breed_and_more'),
    ]

    operations = [
        migrations.AddField(
            model_name='fieldсard',
            name='forest_type',
            field=models.CharField(max_length=100, null=True),
        ),
    ]
