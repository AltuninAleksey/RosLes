# Generated by Django 4.1 on 2024-03-12 13:23

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('djangoForest', '0032_alter_czl_id_main_subject_remove_czl_id_subject_and_more'),
    ]

    operations = [
        migrations.AlterField(
            model_name='czl',
            name='id_subject',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, related_name='slave_subject', to='djangoForest.subjectrf', verbose_name='Подчиненный субъект'),
        ),
    ]
