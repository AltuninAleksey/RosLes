from django.shortcuts import render, redirect
from djangoForest.models import *
from django.views.generic import ListView, DetailView

menu = [
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

def index(request):
    return redirect('purpose')


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