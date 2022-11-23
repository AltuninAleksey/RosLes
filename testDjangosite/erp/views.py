from django.shortcuts import render, redirect
from django.views.generic.edit import FormView, DeleteView, UpdateView
from django.http import HttpResponse, HttpResponseRedirect
from djangoForest.models import *
from django.urls import reverse_lazy
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

class SubjectRFView(FormView):
    queryset = SubjectRF.objects.all()
    template_name = 'erp/html/subjectRF.html'
    form_class = SubjectRfForm
    success_url = reverse_lazy('SubjectRF')

    def post(self, request):
        form = self.get_form()
        if form.is_valid():
            subjectrf = form.save(commit=False)
            subjectrf.save()
            return self.form_valid(form)

    def get_queryset(self):
        return SubjectRF.objects.all()

    def get(self, request):
        return render(request, self.template_name, {'menu': menugue, 'form': self.form_class, 'queryset': self.get_queryset()})


class SubjectRFDelete(DeleteView):
    model = SubjectRF
    success_url = reverse_lazy('SubjectRF')
    template_name = 'erp/html/subjectRF.html'

    def delete(self, request, pk):
        subject = SubjectRF.objects.get(pk=pk)
        subject.delete()
        return redirect('SubjectRF')


class SubjectRFUpdate(UpdateView):
    model = SubjectRF
    success_url = reverse_lazy('SubjectRF')
    template_name = 'erp/html/subjectRF.html'
    form_class = SubjectRFUpdateForm

    def post(self, request, pk):
        subject = SubjectRF.objects.get(pk=pk)
        subject.name_subject_RF = request.POST.get('name_subject_RF')
        subject.save()
        return redirect('SubjectRF')

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name, {'menu': menugue, 'form': self.form_class, 'queryset': self.queryset})


class PostView(FormView):
    model = Post
    success_url = reverse_lazy('PostView')
    queryset = Post.objects.all()
    form_class = PostForm
    template_name = 'erp/html/post.html'

    def post(self, request):
        form = self.get_form()
        if form.is_valid():
            post = form.save(commit=False)
            post.save()
            return self.form_valid(form)

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name, {'menu': menugue, 'form': self.form_class, 'queryset': self.queryset})

    def get_queryset(self):
        queryset = Post.objects.all()
        return queryset


class PostViewDelete(DeleteView):
    model = Post
    success_url = reverse_lazy('PostView')
    template_name = 'erp/html/post.html'

    def delete(self, request, pk):
        post = Post.objects.get(pk=pk)
        post.delete()
        return redirect('PostView')


class PostViewUpdate(UpdateView):
    model = Post
    success_url = reverse_lazy('PostView')
    template_name = 'erp/html/post.html'
    form_class = PostUpdateForm

    def post(self, request, pk):
        post = Post.objects.get(pk=pk)
        post.post_name = request.POST.get('post_name')
        post.save()
        return redirect('PostView')

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name, {'menu': menugue, 'form': self.form_class, 'queryset': self.queryset})


class ForestlyView(FormView):
    model = Forestly
    success_url = reverse_lazy("ForestlyView")
    form_class = ForestlyForm
    template_name = 'erp/html/forestly.html'
    queryset = Forestly.objects.all()

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name,
                      {'menu': menugue, 'form': self.form_class, 'queryset': self.queryset})

    def get_queryset(self):
        queryset = Forestly.objects.all()
        return queryset

def gps_view(request):
    return render(request, 'erp/html/gps.html', {'title': 'GPS', 'menu': menugue})


def branches_view(request):
    return render(request, 'erp/html/branches.html', {'title': 'Филиалы', 'menu': menugue})


def breeds_view(request):
    return render(request, 'erp/html/breeds.html', {'title': 'Породы', 'menu': menugue})


def district_forestly_view(request):
    return render(request, 'erp/html/district_forestly.html', {'title': 'Участоковые лестничества', 'menu': menugue})


def role_view(request):
    return render(request, 'erp/html/role.html', {'title': 'Сотрудники', 'menu': menugue})


def type_of_reproduction_view(request):
    return render(request, 'erp/html/type_of_reproduction.html', {'title': 'Виды воспроизводства', 'menu': menugue})


def working_breeds_view(request):
    return render(request, 'erp/html/working_breeds.html', {'title': 'Рабочие породы', 'menu': menugue})
