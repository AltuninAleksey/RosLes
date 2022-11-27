from django import forms
from djangoForest.models import *

class PostForm(forms.ModelForm):
    class Meta:
        model = Post
        fields = ['post_name']
        widgets = {
            'post_name': forms.TextInput(attrs={'placeholder': 'Наименование должности'}),
        }


class PostUpdateForm(forms.ModelForm):
    class Meta:
        model = Post
        fields = ['post_name']
        widgets = {
            'post_name': forms.TextInput()
        }


# Форма для вывода
class SubjectRfForm(forms.ModelForm):
    class Meta:
        model = SubjectRF
        fields = ['name_subject_RF']
        widgets = {
            'name_subject_RF': forms.TextInput(attrs={'placeholder': 'Наименование субъекта'}),
        }


class SubjectRFUpdateForm(forms.ModelForm):
    class Meta:
        model = SubjectRF
        fields = ['id', 'name_subject_RF']
        widgets = {
            'id': forms.TextInput(),
            'name_subject_RF': forms.TextInput(),
        }

class ForestlyForm(forms.ModelForm):
    class Meta:
        model = Forestly
        fields = ['name_forestly', 'id_subject_rf']
        # queryset_rf = SubjectRF.objects.all()
        id_subject_rf = forms.ModelChoiceField(queryset=SubjectRF.objects.all(), empty_label=None,
                                               to_field_name='subjectrf')
        widgets = {
            'name_forestly': forms.TextInput(attrs={'placeholder': 'Лесничество'}),
        }


class ForestlyUpdateForm(forms.ModelForm):
    class Meta:
        model = Forestly
        fields = ['name_forestly', 'id_subject_rf']
        id_subject_rf = forms.ModelChoiceField(queryset=SubjectRF.objects.all(), empty_label=None,
                                               to_field_name='subjectrf')
        widgets = {
            'name_forestly': forms.TextInput()
        }


class DistrictForestlyForm(forms.ModelForm):
    class Meta:
        model = DistrictForestly
        fields = ['name_district_forestly', 'id_forestly']
        id_forestly = forms.ModelChoiceField(queryset=Forestly.objects.all(), empty_label=None,
                                             to_field_name='forestly')
        widgets = {
            'name_district_forestly': forms.TextInput(attrs={'placeholder': 'Наименование лесничества'},)
        }


class DistrictForestlyUpdateForm(forms.ModelForm):
    class Meta:
        model = DistrictForestly
        fields = ['name_district_forestly', 'id_forestly']
        id_forestly = forms.ModelChoiceField(queryset=Forestly.objects.all(), empty_label=None,
                                             to_field_name='forestly')
        widgets = {
            'name_district_forestly': forms.TextInput()
        }


class RoleForm(forms.ModelForm):
    class Meta:
        model = Role
        fields = ['name_role']
        widgets = {
            'name_role': forms.TextInput(attrs={'placeholder': 'Наименование'})
        }


class RoleUpdateForm(forms.ModelForm):
    class Meta:
        model = Role
        fields = ['name_role']
        widgets = {
            'name_role': forms.TextInput()
        }


class BranchForm(forms.ModelForm):
    class Meta:
        model = Branches
        fields = ['name_branch']
        widgets = {
            'name_branch': forms.TextInput(attrs={'placeholder': 'Наименование филиала'}),
        }


class BranchUpdateForm(forms.ModelForm):
    class Meta:
        model = Branches
        fields = ['name_branch']
        widgets = {
            'name_branch': forms.TextInput(),
        }


class GPSForm(forms.ModelForm):
    class Meta:
        model = GPS
        fields = ['latitude', 'longitude', 'flag_center']
        id_sample = forms.ModelChoiceField(queryset=Sample.objects.all(), empty_label=None,
                                           to_field_name='sample')
        widgets = {
            'latitude': forms.TextInput(attrs={'placeholder': 'Широта'}),
            'longitude': forms.TextInput(attrs={'placeholder': 'Долгота'}),
            'flag_center': forms.TextInput(attrs={'placeholder': 'Флаг'}),
        }