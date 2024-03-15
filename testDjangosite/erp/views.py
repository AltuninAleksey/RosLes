from django.shortcuts import render, redirect
from django.views.generic.edit import FormView, DeleteView, UpdateView
from django.views.generic import ListView
from djangoForest.models import *
from django.urls import reverse_lazy
from .forms import *

# menudoc = ["Перечет на пробной площади", "Переченая ведомасть участка", "Объединить пробы"]
# menugue = ["Должности", "Лесничества", "Лесообразующие породы",
#            "Сотрудник", "Субъекты РФ", "Участковое лесничество",
#            "Филиалы", "Синхронизация справочников"]

menudoc = [
    {'title': 'Перечет на пробной площади', 'url_name': 'listregion'},
    {'title': 'Перечетная ведомость участка', 'url_name': 'sample'}
]

menugue = [
    {'title': "Должности", 'url_name': 'post'},
    {'title': "Субъекты РФ", 'url_name': 'subjectRF'},
    {'title': "Лесничества", 'url_name': 'forestly'},
    {'title': "Участковое лесничество", 'url_name': 'district_forestly'},
    {'title': "Роль", 'url_name': 'role'},
    {'title': "Филиалы", 'url_name': 'branches'},
    {'title': "GPS", 'url_name': 'gps'},
    {'title': "Синхронизация справочников", 'url_name': 'guide'}
           ]
# Create your views here.

class ListRegionView(FormView):
    model = ListRegion
    template_name = 'erp/html/listregion.html'

    def get(self, request, pk=None):
        return render(request, self.template_name, {'menu': menudoc})


class SampleView(FormView):
    model = List
    template_name = 'erp/html/sample.html'

    def get(self, request):
        return render(request, self.template_name, {'menu': menugue})


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
        return render(request, self.template_name, {'menu': menugue, 'form': self.form_class,
                                                    'queryset': self.get_queryset()})


class SubjectRFDelete(DeleteView):
    model = SubjectRF
    success_url = reverse_lazy('SubjectRF')
    template_name = 'erp/html/subjectRF.html'

    def delete(self, request, pk):
        subject = SubjectRF.objects.get()
        subject.delete()
        return redirect('SubjectRF')


class SubjectRFUpdate(UpdateView):
    model = SubjectRF
    success_url = reverse_lazy('SubjectRF')
    template_name = 'erp/html/subjectRF.html'
    form_class = SubjectRFUpdateForm

    def post(self, request, pk):
        subject = SubjectRF.objects.get()
        subject.name_subject_RF = request.POST.get('name_subject_RF')
        subject.save()
        return redirect('SubjectRF')

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name, {'menu': menugue, 'form': self.form_class,
                                                    'queryset': self.queryset})


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
        return render(request, self.template_name, {'menu': menugue, 'form': self.form_class,
                                                    'queryset': self.queryset})

    def get_queryset(self):
        queryset = Post.objects.all()
        return queryset


class PostViewDelete(DeleteView):
    model = Post
    success_url = reverse_lazy('PostView')
    template_name = 'erp/html/post.html'

    def delete(self, request, pk):
        post = Post.objects.get()
        post.delete()
        return redirect('PostView')


class PostViewUpdate(UpdateView):
    model = Post
    success_url = reverse_lazy('PostView')
    template_name = 'erp/html/post.html'
    form_class = PostUpdateForm

    def post(self, request, pk):
        post = Post.objects.get()
        post.post_name = request.POST.get('post_name')
        post.save()
        return redirect('PostView')

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name, {'menu': menugue, 'form': self.form_class,
                                                    'queryset': self.queryset})


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

    def post(self, request):
        form = self.get_form()
        if form.is_valid():
            forestly = form.save(commit=False)
            forestly.save()
            return self.form_valid(form)

    def get_queryset(self):
        queryset = Forestly.objects.all()
        return queryset


class ForestlyViewDelete(DeleteView):
    model = Forestly
    success_url = reverse_lazy('ForestlyView')
    template_name = 'erp/html/forestly.html'

    def post(self, request, pk):
        forestly = Forestly.objects.get()
        forestly.delete()
        return redirect('ForestlyView')


class ForestlyUpdateView(UpdateView):
    model = Forestly
    success_url = reverse_lazy('ForestlyView')
    template_name = 'erp/html/forestly.html'

    def post(self, request, pk):
        forestly = Forestly.objects.get()
        forestly.name_forestly = request.POST.get('name_forestly')
        # forestly.id_subject_rf = request.POST.get('subjectrf')
        forestly.save()
        return redirect('ForestlyView')


class DistrictForestlyView(FormView):
    model = DistrictForestly
    success_url = reverse_lazy("DistrictView")
    form_class = DistrictForestlyForm
    template_name = 'erp/html/district_forestly.html'
    queryset = DistrictForestly.objects.all()

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name,
                      {'menu': menugue, 'form': self.form_class, 'queryset': self.queryset})

    def post(self, request):
        form = self.get_form()
        if form.is_valid():
            district_forestly = form.save(commit=False)
            district_forestly.save()
            return self.form_valid(form)

    def get_queryset(self):
        queryset = DistrictForestly.objects.all()
        return queryset


class DistrictForestlyDelete(DeleteView):
    model = DistrictForestly
    success_url = reverse_lazy('DistrictView')
    template_name = 'erp/html/district_forestly.html'

    def post(self, request, pk):
        district_forestly = DistrictForestly.objects.get()
        district_forestly.delete()
        return redirect('DistrictView')


class DistrictForestlyUpdateView(UpdateView):
    model = DistrictForestly
    success_url = reverse_lazy('DistrictView')
    template_name = 'erp/html/district_forestly.html'

    def post(self, request, pk):
        district_forestly = DistrictForestly.objects.get()
        district_forestly.name_district_forestly = request.POST.get('name_district_forestly')
        # forestly.id_subject_rf = request.POST.get('subjectrf')
        district_forestly.save()
        return redirect('DistrictView')


class RoleView(FormView):
    model = Role
    success_url = reverse_lazy('RoleView')
    template_name = 'erp/html/role.html'
    form_class = RoleForm
    queryset = Role.objects.all()

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name,
                      {'menu': menugue, 'form': self.form_class, 'queryset': self.queryset})

    def post(self, request):
        form = self.get_form()
        if form.is_valid():
            role = form.save(commit=False)
            role.save()
            return self.form_valid(form)

    def get_queryset(self):
        queryset = Role.objects.all()
        return queryset


class RoleDelete(DeleteView):
    model = Role
    success_url = reverse_lazy('RoleView')
    template_name = 'erp/html/role.html'

    def post(self, request, pk):
        role = Role.objects.get()
        role.delete()
        return redirect('RoleView')


class RoleUpdate(UpdateView):
    model = Role
    success_url = reverse_lazy('RoleView')
    template_name = 'erp/html/role.html'

    def post(self, request, pk):
        role = Role.objects.get()
        role.name_role = request.POST.get('name_role')
        # instanse = SubjectRF.objects.get(pk=request.POST.get('subjectrf')
        # forestly.id_subject_rf = instance.name_subject_RF
        # forestly.id_subject_rf = request.POST.get('subjectrf')
        role.save()
        return redirect('RoleView')


class BranchesView(FormView):
    model = Branches
    success_url = reverse_lazy('BranchesView')
    template_name = 'erp/html/branches.html'
    form_class = BranchForm
    queryset = Branches.objects.all()

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name,
                      {'menu': menugue, 'form': self.form_class, 'queryset': self.queryset})

    def post(self, request):
        form = self.get_form()
        if form.is_valid():
            branch = form.save(commit=False)
            branch.save()
            return self.form_valid(form)

    def get_queryset(self):
        queryset = Branches.objects.all()
        return queryset


class BranchesDelete(DeleteView):
    model = Branches
    success_url = reverse_lazy('BranchesView')
    template_name = 'erp/html/branches.html'

    def post(self, request, pk):
        branch = Branches.objects.get()
        branch.delete()
        return redirect('BranchesView')


class BranchesUpdate(UpdateView):
    model = Role
    success_url = reverse_lazy('BranchesView')
    template_name = 'erp/html/branches.html'

    def post(self, request, pk):
        branch = Branches.objects.get()
        branch.name_branch = request.POST.get('name_branch')
        branch.save()
        return redirect('BranchesView')


class GpsView(FormView):
    model = GPS
    success_url = reverse_lazy('GPSView')
    template_name = 'erp/html/gps.html'
    form_class = GPSForm
    queryset = GPS.objects.all()

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name,
                      {'menu': menugue, 'form': self.form_class, 'queryset': self.queryset})

    def post(self, request):
        form = self.get_form()
        if form.is_valid():
            gps = form.save(commit=False)
            gps.save()
            return self.form_valid(form)

    def get_queryset(self):
        queryset = GPS.objects.all()
        return queryset


def breeds_view(request):
    return render(request, 'erp/html/breeds.html', {'title': 'Породы', 'menu': menugue})


def type_of_reproduction_view(request):
    return render(request, 'erp/html/type_of_reproduction.html', {'title': 'Виды воспроизводства', 'menu': menugue})


def working_breeds_view(request):
    return render(request, 'erp/html/working_breeds.html', {'title': 'Рабочие породы', 'menu': menugue})

def getRecalculationOnTrialArea(request):
    return render(request, 'erp/html/recalculationOnTrialArea.html')

def getRecalculationOnTrialAreaDetail(request):
    return render(request, 'erp/html/recalculationOnTrialAreaDetail.html', {'idDocument': request.GET.get('id')})

def getRecalculationOnTrialAreaDetailNew(request):
    return render(request, 'erp/html/recalculationOnTrialAreaDetailNew.html')

def getListRegions(request):
    return render(request, 'erp/html/listRegions.html')

def getStatementRecalculations(request):
    return render(request, 'erp/html/statementRecalculations.html')

def getMergeStatementRecalculations(request):
    return render(request, 'erp/html/mergeStatementRecalculations.html')

def getStatementRecalculationsDetail(request):
    return render(request, 'erp/html/statementRecalculationsDetail.html', {'idDocument': request.GET.get('id')})

def getNewStatementRecalculationsDetail(request):
    return render(request, 'erp/html/newStatementRecalculationsDetail.html')

def getDescriptionListLand(request):
    return render(request, 'erp/html/descriptionListLand.html')

def getFieldCard(request):
    return render(request, 'erp/html/fieldCard.html')

def getRecalculatingDetail(request):
    return render(request, 'erp/html/recalculatingDetail.html', {'idDocument': request.GET.get('id'), 'idParent': request.GET.get('idParent')})

def getPlotDescription(request):
    return render(request, 'erp/html/plotDescription.html', {'idDocument': request.GET.get('id'), 'idParent': request.GET.get('idParent')})
def getNewPlotDescription(request):
    return render(request, 'erp/html/newPlotDescription.html')

def getPrintFieldCard(request):
    return render(request, 'erp/html/printFieldCard.html', {'idDocument': request.GET.get('id'), 'idParent': request.GET.get('idParent')})

def getNewFieldCard(request):
    return render(request, 'erp/html/newFieldCard.html')
def getAvtorization(request):
    return render(request, 'erp/html/avtorization.html')
def getRegestration(request):
    return render(request, 'erp/html/regestration.html')