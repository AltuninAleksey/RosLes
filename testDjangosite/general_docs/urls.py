from django.urls import path
from general_docs.views import *


urlpatterns = [
    path('general_docs', index),
    path('PurposeOfForests', PurposeOfForestsView.as_view(), name='purpose'),
    path('ForestProtectionCategory', ForestProtectionCategoryView.as_view()),
    path('CategoryOfForestFundLands', CategoryOfForestFundLandsView.as_view()),
    path('MethodOfReforestations', MethodOfReforestationView.as_view()),
    path('BonitetOrlov', BonitetOrlovView.as_view()),
    path('TypeForestGrowingConditions', TypeForestGrowingConditionsView.as_view()),
    path('economy', EconomyView.as_view()),
    path('AccordanceMolodKrAndTPPL', AccordanceMolodKrAndTPPLView.as_view()),
    path('AccordanceNoneAccordanceEconomy', AccordanceNoneAccordanceEconomyView.as_view()),
    path('CategoryGroundLFInNoneAccordance', CategoryGroundLFInNoneAccordanceView.as_view()),
    path('ForestAreas', ForestAreasView.as_view()),

]

