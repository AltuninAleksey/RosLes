from django.shortcuts import render, redirect
from django.urls import reverse_lazy

from djangoForest.models import *
from django.views.generic import ListView, DetailView, FormView

from erp.forms import SubjectRfForm

menu = [
    {'title': "Субъекты РФ", 'url_name': 'subjectRF'},
    {'title': "Лесничество", 'url_name': 'forestly'},
    {'title': "Участковое лесничество", 'url_name': 'district_forestly'},
    {'title': "Квартал", 'url_name': 'quarter'},
    {'title': 'Целевое назначение лесов', 'url_name': 'PurposeOfForests'},
    {'title': 'Категория защитности лесов', 'url_name': 'ForestProtectionCategory'},
    {'title': 'Категория земель лесного фонда', 'url_name': 'CategoryOfForestFundLands'},
    {'title': 'Способ лесовосстановления', 'url_name': 'MethodOfReforestations'},
    {'title': 'Бонитет по Орлову', 'url_name': 'BonitetOrlov'},
    {'title': 'Тип лесорастительный условий', 'url_name': 'TypeForestGrowingConditions'},
    {'title': 'Хозяйство', 'url_name': 'economy'},
    {'title': 'Соответ. молодняка кр. и тр. ПЛ', 'url_name': 'AccordanceMolodKrAndTPPL'},
    {'title': 'Соответ. не соответ. хозяйству', 'url_name': 'AccordanceNoneAccordanceEconomy'},
    {'title': 'Кат. земель лф в случ. несоотв.', 'url_name': 'CategoryGroundLFInNoneAccordance'},
    {'title': 'Лесные районы', 'url_name': 'ForestAreas'},

]

menugmvl = [
    {'title': "Должности", 'url_name': 'post'},
    {'title': "Роль", 'url_name': 'role'},
    {'title': "Филиалы", 'url_name': 'branches'},
    {'title': "Сотрудники", 'url_name': 'profile'},
    {'title': "Рабочие породы", 'url_name': 'working_breeds'},
    {'title': "GPS точка", 'url_name': 'gps'},
    {'title': "Порода", 'url_name': 'breed'},
    {'title': "Перечет", 'url_name': 'list'},
    {'title': "Вид воспроизводства", 'url_name': 'type_of_reproduction'},
    {'title': "Перечетная ведомость участка", 'url_name': 'listregion'},
    {'title': "Проба", 'url_name': 'sample'},
]

# def index(request):
#     return redirect('purpose')

def index(request):
    return render(request, 'general_docs/html/general_books.html', {'menu': menu})

def gmvl(request):
    return render(request, 'general_docs/html/gmvl.html', {'menu': menugmvl})

class ForestAreasView(ListView):
    model = ForestAreas
    template_name = 'general_docs/html/ForestAreas.html'
    queryset = ForestAreas.objects.all()

    def get_queryset(self):
        return ForestAreas.objects.all()

    def get(self, request):
        return render(request, self.template_name, {'menu': menu, 'queryset': self.get_queryset()})


class PurposeOfForestsView(DetailView):
    model = PurposeOfForests
    template_name = 'general_docs/html/PurposeOfForests.html'
    queryset = PurposeOfForests.objects.all()

    def get_queryset(self):
        return PurposeOfForests.objects.all()

    def get(self, request):
        return render(request, self.template_name, {'menu': menu, 'queryset': self.get_queryset()})


class ForestProtectionCategoryView(ListView):
    template_name = 'general_docs/html/ForestProtectionCategory.html'
    queryset = ForestProtectionCategory.objects.all()

    def get_queryset(self):
        return ForestProtectionCategory.objects.all()

    def get(self, request):
        return render(request, self.template_name, {'menu': menu, 'queryset': self.get_queryset()})


class CategoryOfForestFundLandsView(ListView):
    template_name = 'general_docs/html/CategoryOfForestFundLands.html'
    queryset = CategoryOfForestFundLands.objects.all()

    def get_queryset(self):
        return CategoryOfForestFundLands.objects.all()

    def get(self, request):
        return render(request, self.template_name, {'menu': menu, 'queryset': self.get_queryset()})


class MethodOfReforestationView(ListView):
    template_name = 'general_docs/html/MethodOfReforestations.html'
    queryset = MethodOfReforestation.objects.all()

    def get_queryset(self):
        return MethodOfReforestation.objects.all()

    def get(self, request):
        return render(request, self.template_name, {'menu': menu, 'queryset': self.get_queryset()})


class BonitetOrlovView(ListView):
    template_name = 'general_docs/html/BonitetOrlov.html'
    queryset = BonitetOrlov.objects.all()

    def get_queryset(self):
        return BonitetOrlov.objects.all()

    def get(self, request):
        return render(request, self.template_name, {'menu': menu, 'queryset': self.get_queryset()})


class TypeForestGrowingConditionsView(ListView):
    template_name = 'general_docs/html/TypeForestGrowingConditions.html'
    queryset = TypeForestGrowingConditions.objects.all()

    def get_queryset(self):
        return TypeForestGrowingConditions.objects.all()

    def get(self, request):
        return render(request, self.template_name, {'menu': menu, 'queryset': self.get_queryset()})


class EconomyView(ListView):
    template_name = 'general_docs/html/economy.html'
    queryset = Economy.objects.all()

    def get_queryset(self):
        return Economy.objects.all()

    def get(self, request):
        return render(request, self.template_name, {'menu': menu, 'queryset': self.get_queryset()})


class AccordanceMolodKrAndTPPLView(ListView):
    template_name = 'general_docs/html/AccordanceMolodKrAndTPPL.html'
    queryset = AccordanceMolodKrAndTPPL.objects.all()

    def get_queryset(self):
        return AccordanceMolodKrAndTPPL.objects.all()

    def get(self, request):
        return render(request, self.template_name, {'menu': menu, 'queryset': self.get_queryset()})


class AccordanceNoneAccordanceEconomyView(ListView):
    template_name = 'general_docs/html/AccordanceNoneAccordanceEconomy.html'
    queryset = AccordanceNoneAccordanceEconomy.objects.all()

    def get_queryset(self):
        return AccordanceNoneAccordanceEconomy.objects.all()

    def get(self, request):
        return render(request, self.template_name, {'menu': menu, 'queryset': self.get_queryset()})


class CategoryGroundLFInNoneAccordanceView(ListView):
    template_name = 'general_docs/html/CategoryGroundLFInNoneAccordance.html'
    queryset = CategoryGroundLFInNoneAccordance.objects.all()

    def get_queryset(self):
        return CategoryGroundLFInNoneAccordance.objects.all()

    def get(self, request):
        return render(request, self.template_name, {'menu': menu, 'queryset': self.get_queryset()})

class SubjectRFView(ListView):
    queryset = SubjectRF.objects.all()
    template_name = 'general_docs/html/subjectRF.html'
    form_class = SubjectRfForm
    success_url = reverse_lazy('SubjectRF')

    def get_queryset(self):
        return SubjectRF.objects.all()

    def get(self, request):
        return render(request, self.template_name, {'menu': menu, 'queryset': self.get_queryset()})

class ForestlyView(ListView):
    model = Forestly
    success_url = reverse_lazy("ForestlyView")
    template_name = 'general_docs/html/forestly.html'
    queryset = Forestly.objects.all()

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name,{'menu': menu, 'queryset': self.queryset})

    def get_queryset(self):
        queryset = Forestly.objects.all()
        return queryset

class DistrictForestlyView(ListView):
    model = DistrictForestly
    success_url = reverse_lazy("DistrictView")
    template_name = 'general_docs/html/district_forestly.html'
    queryset = DistrictForestly.objects.all()

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name,{'menu': menu, 'queryset': self.queryset})

    def get_queryset(self):
        queryset = DistrictForestly.objects.all()
        return queryset

class QuarterView(ListView):
    queryset = Quarter.objects.all()
    template_name = 'general_docs/html/quarter.html'
    success_url = reverse_lazy('QuarterView')

    def get_queryset(self):
        return Quarter.objects.all()

    def get(self, request):
        return render(request, self.template_name, {'menu': menu, 'queryset': self.get_queryset()})

class PostView(ListView):
    model = Post
    success_url = reverse_lazy('PostView')
    queryset = Post.objects.all()
    template_name = 'general_docs/html/post.html'

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name, {'menu': menugmvl,'queryset': self.queryset})
    def get_queryset(self):
        queryset = Post.objects.all()
        return queryset

class RoleView(ListView):
    model = Role
    success_url = reverse_lazy('RoleView')
    template_name = 'general_docs/html/role.html'
    queryset = Role.objects.all()

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name,
                      {'menu': menugmvl, 'queryset': self.queryset})
    def get_queryset(self):
        queryset = Role.objects.all()
        return queryset
class BranchesView(ListView):
    model = Branches
    success_url = reverse_lazy('BranchesView')
    template_name = 'general_docs/html/branches.html'
    queryset = Branches.objects.all()

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name,
                      {'menu': menugmvl, 'queryset': self.queryset})

    def get_queryset(self):
        queryset = Branches.objects.all()
        return queryset

class ProfileView(ListView):
    model = Profile
    success_url = reverse_lazy('ProfileView')
    template_name = 'general_docs/html/profile.html'
    queryset = Profile.objects.all()

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name,
                      {'menu': menugmvl, 'queryset': self.queryset})

    def get_queryset(self):
        queryset = Profile.objects.all()
        return queryset

class WorkingBreedsView(ListView):
    model = WorkingBreeds
    success_url = reverse_lazy('WorkingBreedsView')
    template_name = 'general_docs/html/working_breeds.html'
    queryset = WorkingBreeds.objects.all()

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name,
                      {'menu': menugmvl, 'queryset': self.queryset})

    def get_queryset(self):
        queryset = WorkingBreeds.objects.all()
        return queryset

class BreedsView(ListView):
    model = Breed
    success_url = reverse_lazy('BreedsView')
    template_name = 'general_docs/html/breed.html'
    queryset = Breed.objects.all()

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name,
                      {'menu': menugmvl, 'queryset': self.queryset})

    def get_queryset(self):
        queryset = Breed.objects.all()
        return queryset
class GpsView(ListView):
    model = GPS
    success_url = reverse_lazy('GPSView')
    template_name = 'general_docs/html/gps.html'
    queryset = GPS.objects.all()

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name,
                      {'menu': menugmvl, 'queryset': self.queryset})
    def get_queryset(self):
        queryset = GPS.objects.all()
        return queryset

class ListView(ListView):
    model = List
    success_url = reverse_lazy('LISTView')
    template_name = 'general_docs/html/list.html'
    queryset = List.objects.all()

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name,
                      {'menu': menugmvl, 'queryset': self.queryset})
    def get_queryset(self):
        queryset = List.objects.all()
        return queryset

class TypeOfReproductionView(ListView):
    model = Reproduction
    success_url = reverse_lazy('TypeOfReproductionView')
    template_name = 'general_docs/html/type_of_reproduction.html'
    queryset = Reproduction.objects.all()

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name,
                      {'menu': menugmvl, 'queryset': self.queryset})

    def get_queryset(self):
        queryset = Reproduction.objects.all()
        return queryset

class ListRegionView(ListView):
    model = ListRegion
    success_url = reverse_lazy('ListRegionView')
    template_name = 'general_docs/html/listregion.html'
    queryset = ListRegion.objects.all()

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name,
                      {'menu': menugmvl, 'queryset': self.queryset})

    def get_queryset(self):
        queryset = ListRegion.objects.all()
        return queryset

class SampleView(FormView):
    model = Sample
    template_name = 'general_docs/html/sample.html'
    success_url = reverse_lazy('SampleView')
    queryset = Sample.objects.all()

    def get(self, request):
        self.queryset = self.get_queryset()
        return render(request, self.template_name,
                      {'menu': menugmvl, 'queryset': self.queryset})

    def get_queryset(self):
        queryset = Sample.objects.all()
        return queryset