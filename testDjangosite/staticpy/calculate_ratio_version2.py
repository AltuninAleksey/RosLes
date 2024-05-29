

def calculate_all_plants_of_breed(id_breed, request):
    total = 0
    count_diameter = 0
    count_height = 0
    avg_height = 0
    avg_diameter = 0
    avg_age = 0
    count_age = 0
    for i in request:
        if i['id_breed'] == id_breed:
            if type(i['count_of_plants']) == int:
                if i['count_of_plants'] == 0:
                    continue
                total += i['count_of_plants']
            if i['avg_height'] != None:
                avg_height += i['avg_height']
                count_height += 1
            if i['avg_diameter'] != None:
                avg_diameter += i['avg_diameter']
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

    return {"id_breed": id_breed, "total": total, "avg_height": avg_height, "avg_diameter": avg_diameter, "avg_age": avg_age}


def calculate_all_plants(request):
    total = 0
    for i in request:
        if type(i['count_of_plants']) == int:
            total += i['count_of_plants']

    return total


def percent_of_breed(return_data, all_plants):
    ratio_composition = 0
    for i in return_data:
        ratio_composition = round((i['count_of_plants'] * 100) / all_plants, 0)/100
        if ratio_composition < 0.1:
            ratio_composition = 0
        i.update({"ratio_composition": ratio_composition})

    return return_data

# def form_json_data(request):
#     return {"id_breed": request['id_breed'], "avg_height": request['avg_height'], "avg_diameter": request['avg_diameter'], "count_of_plants": request['count_of_plants']}

def calculate(data):
    breed_list = []
    all_plants = calculate_all_plants(data)
    return_data = []
    for i in data:
        print(i)
        if i['id_breed'] not in breed_list:
            return_data.append({
                'id_breed': i['id_breed'], 'age': i['age'],
                'count_of_plants': i['count_of_plants'], 'id_sample': i['id_sample'],"avg_height": i['avg_height'], "avg_diameter": i['avg_diameter'], 'id_list': i['id'], })

    # for i in data:
        # return_data.append(form_json_data(i))
    # for k in breed_list:
    #     return_data.append(calculate_all_plants_of_breed(k, data))
    # for i in data:
    #     print(i['count_of_plants'])

    return_data = percent_of_breed(return_data, all_plants)
    print(breed_list)
    print(all_plants)

    return return_data

