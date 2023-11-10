from django.urls import path
from general_docs.views import *


urlpatterns = [
    path('', index),
    path('general_books', index),
    path('gmvl', gmvl),
    path('subjectRF', SubjectRFView.as_view(), name='SubjectRF'),
    path('forestly', ForestlyView.as_view(), name="ForestlyView"),
    path('district_forestly', DistrictForestlyView.as_view(), name='DistrictView'),
    path('quarter', QuarterView.as_view(), name="QuarterView"),
    path('post', PostView.as_view(), name="PostView"),
    path('role', RoleView.as_view(), name='RoleView'),
    path('branches', BranchesView.as_view(), name='BranchesView'),
    path('profile', ProfileView.as_view(), name='ProfileView'),
    path('working_breeds', WorkingBreedsView.as_view(), name='WorkingBreedsView'),
    path('breed', BreedsView.as_view(), name='BreedsView'),
    path('gps', GpsView.as_view(), name='GPSView'),
    path('list', ListView.as_view(), name='LISTView'),
    path('type_of_reproduction', TypeOfReproductionView.as_view(), name='TypeOfReproductionView'),
    path('listregion', ListRegionView.as_view(), name='ListRegionView'),
    path('sample', SampleView.as_view(), name='SampleView'),

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
    path('djangoForest_federaldistricts', djangoForest_federaldistrictsView.as_view()),
    path('djangoForest_forestcareactivities', djangoForest_forestcareactivitiesView.as_view()),
    path('djangoForest_methodforestrestoration', djangoForest_methodforestrestorationView.as_view()),
    path('djangoForest_recommendation', djangoForest_recommendationView.as_view()),
    path('djangoForest_ForestDistricts', djangoForest_ForestDistrictsView.as_view()),

]

