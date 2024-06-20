def calculate_all_plants_of_breed(id_breed, request):
    all_plants = calculate_all_plants(request)
    total = 0
    count_diameter = 0
    count_height = 0
    avg_height = 0
    avg_diameter = 0
    avg_age = 0
    count_age = 0
    ratio_composition = 0
    total_now = 0
    avg_height_total = 0
    avg_diameter_total = 0
    for i in request:
        if i['id_breed'] == id_breed:
            if type(i['count_of_plants']) == int:
                if i['count_of_plants'] == 0:
                    continue
                total += i['count_of_plants']
                total_now = i['count_of_plants']
            if i['avg_height'] != None:
                avg_height = i['avg_height']
                avg_height_total += (avg_height*total_now)
                count_height += 1
            if i['avg_diameter'] != None:
                avg_diameter = i['avg_diameter']
                avg_diameter_total += (avg_diameter*total_now)
                count_diameter += 1
            if i['age'] != None:
                avg_age += i['age']
                count_age += 1
    if not count_height == 0:
        avg_height = avg_height/count_height
    if not count_diameter == 0:
        avg_diameter = avg_diameter/count_diameter
    if not count_age == 0:
        avg_age = avg_age/count_age
    if all_plants != 0:
        ratio_composition = ((total*100)/all_plants)/10
    if avg_height_total != 0:
        avg_height_total /= total
    if avg_diameter_total != 0:
        avg_diameter_total /= total
    count_of_plants = (total*10000)/400
    return {"id_breed": id_breed, "total": count_of_plants,
            "avg_height": avg_height_total, "avg_diameter": avg_diameter_total, "avg_age": avg_age,
            "ratio_composition": ratio_composition}


def calculate_all_plants(request):
    total = 0
    for i in request:
        if type(i['count_of_plants']) == int:
            total += i['count_of_plants']

    return total


def percent_of_breed(return_data, all_plants):
    ratio_composition = 0
    for i in return_data:
        ratio_composition = round((i['total'] * 100) / all_plants, 0)/100
        if ratio_composition < 0.1:
            ratio_composition = 0
        i.update({"ratio_composition": ratio_composition})

    return return_data


def calculate(data):
    breed_list = []
    all_plants = calculate_all_plants(data)
    return_data = []
    for i in data:
        print(i)
        if i['id_breed'] not in breed_list:
            breed_list.append(i['id_breed'])

    for k in breed_list:
        return_data.append(calculate_all_plants_of_breed(k, data))

    # return_data = percent_of_breed(return_data, all_plants)
    print(breed_list)
    print(all_plants)

    return return_data