# Generated by Django 4.1.3 on 2022-12-18 15:27

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('djangoForest', '0009_checktrigger'),
    ]

    operations = [
        migrations.CreateModel(
            name='CheckAlterTables',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('bool', models.BooleanField()),
            ],
            options={
                'verbose_name': 'Trigger',
            },
        ),
        migrations.DeleteModel(
            name='CheckTrigger',
        ),
    ]
