from django.urls import path
from .views import des_tool_view

urlpatterns = [
    path('', des_tool_view, name='home'),   # root URL of your app
    path('des/', des_tool_view, name='des_tool'),  # optional duplicate route
]
