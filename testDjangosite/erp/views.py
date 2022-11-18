from django.shortcuts import render
from django.views.generic.edit import FormView
from django.http import HttpResponse, HttpResponseRedirect
from djangoForest.models import *


from .forms import *

menudoc = ["Перечет на пробной площади", "Переченая ведомасть участка", "Объединить пробы"]
# menugue = ["Должности", "Лесничества", "Лесообразующие породы",
#            "Сотрудник", "Субъекты РФ", "Участковое лесничество",
#            "Филиалы", "Синхронизация справочников"]

menugue = [
    {'title': "Должности", 'url_name': 'post'},
    {'title': "Субъекты РФ", 'url_name': 'subjectRF'},
    {'title': "Лесничества", 'url_name': 'forestly'},
    {'title': "Участковое лесничество", 'url_name': 'district_forestly'},
    {'title': "Сотрудник", 'url_name': 'role'},
    {'title': "Филиалы", 'url_name': 'branches'},
    {'title': "Синхронизация справочников", 'url_name': 'guide'}
           ]
# Create your views here.


def index(request):
    return render(request, 'erp/html/index.html')


def base(request):
    return render(request, 'erp/html/base.html')


def documents(request):
    return render(request, 'erp/html/documents.html')


def guide(request):
    return render(request, 'erp/html/guide.html', {'menu': menugue})


def index11(request):
    return render(request, 'erp/html/index11.html')


def subjectRFview(request):
    if request.method == "POST":
        form = SubjectRfForm(request.POST)
        if form.is_valid():
            print(form.cleaned_data)
            try:
                SubjectRF.objects.create(**form.cleaned_data)
            except:
                form.add_error("Ошибка")
    else:
        form = SubjectRfForm()
    return render(request, 'erp/html/subjectRF.html', {'menu': menugue, 'form': form, 'title': 'Субъекты РФ'})


# class SubjectRFView(FormView):
#     template_name = 'erp/html/subjectRF.html'
#     form_class = SubjectRfForm
#
#     def get(self, request):
#         return render(request, self.template_name, {'menu': menugue})


def post_view(request):
    return render(request, 'erp/html/post.html', {'title': 'Должности', 'menu': menugue})


def gps_view(request):
    return render(request, 'erp/html/gps.html', {'title': 'GPS', 'menu': menugue})


def branches_view(request):
    return render(request, 'erp/html/branches.html', {'title': 'Филиалы', 'menu': menugue})


def breeds_view(request):
    return render(request, 'erp/html/breeds.html', {'title': 'Породы', 'menu': menugue})


def district_forestly_view(request):
    return render(request, 'erp/html/district_forestly.html', {'title': 'Участоковые лестничества', 'menu': menugue})


def forestly_view(request):
    return render(request, 'erp/html/forestly.html', {'title': 'Лесничества', 'menu': menugue})


def role_view(request):
    return render(request, 'erp/html/role.html', {'title': 'Сотрудники', 'menu': menugue})


def type_of_reproduction_view(request):
    return render(request, 'erp/html/type_of_reproduction.html', {'title': 'Виды воспроизводства', 'menu': menugue})


def working_breeds_view(request):
    return render(request, 'erp/html/working_breeds.html', {'title': 'Рабочие породы', 'menu': menugue})
