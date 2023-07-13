
def calculate_max_plants(request):
    total = 0
    for i in request:
        if type(i['count_of_plants']) == int:
            total += i['count_of_plants']
    return total


def calculate_coeff(request):
    total = calculate_max_plants(request)
    for i in request:
        if type(i['count_of_plants']) == int:
            value_ratio = round((i['count_of_plants'] * 10)/total)
        else:
            value_ratio = '+'
        if value_ratio == 0:
            value_ratio = "+"
        i.update({"ratio_composition": value_ratio})
    return request
