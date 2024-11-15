from xml.dom.minidom import Document

from docx.shared import Pt
from jinja2.runtime import new_context

from testDjangosite.settings import BASE_DIR
from docxtpl import DocxTemplate
from docx import Document
import os
import uuid


# название включает себя fieldcard_{id}
# название описание участка тоже самое
# две новых таблицы для хранения ссылок на полевую карточку и описание участка

def forming_docx_fieldcard(context: dict):
    doc = DocxTemplate(os.path.abspath(f"{BASE_DIR}/media/fieldcard.docx"))
    doc.render(context)
    filepath = os.path.abspath(f"{BASE_DIR}/media/docx_files/fieldcards/fieldcard_{context['id']}.docx")
    doc.save(filepath)
    return filepath.split("testDjangosite")[1]


def forming_docx_desc_region(context: dict):
    doc = DocxTemplate(os.path.abspath(f"{BASE_DIR}/media/desc_region.docx"))
    doc.render(context)
    filepath = os.path.abspath(f"{BASE_DIR}/media/docx_files/desc_region/desc_region_{context['id']}.docx")
    doc.save(filepath)
    return filepath.split("testDjangosite")[1]


def get_repro(data: dict) -> None:
    def from_context_get_repro_1(data: dict) -> dict:
        res = []
        hg_data_1 = []
        breeds_1_data = []
        m_h_data = []

        for i in data['data']:
            if i['id_type_of_reproduction'] == 1:
                if i['id_breed'] not in breeds_1_data:
                    breeds_1_data.append(i['id_breed'])
                hg_data_1.append(i['to0_2'])
                hg_data_1.append(i['from0_21To0_5'])
                hg_data_1.append(i['from0_6To1_0'])
                hg_data_1.append(i['from1_1to1_5'])
                hg_data_1.append(i['from1_5'])
                res.append(i)

        data.update({"repro_1": res, "hg_data_1": hg_data_1, "breeds_1_data": breeds_1_data})
        return data

    def from_context_get_repro_2(data: dict) -> dict:
        res = []
        hg_data_2 = []
        breeds_2_data = []

        for i in data['data']:
            if i['id_type_of_reproduction'] == 2:
                if i['id_breed'] not in breeds_2_data:
                    breeds_2_data.append(i['id_breed'])
                hg_data_2.append(i['to0_2'])
                hg_data_2.append(i['from0_21To0_5'])
                hg_data_2.append(i['from0_6To1_0'])
                hg_data_2.append(i['from1_1to1_5'])
                hg_data_2.append(i['from1_5'])
                res.append(i)

        data.update({"repro_2": res, "hg_data_2": hg_data_2, "breeds_2_data": breeds_2_data})
        return data

    def from_context_get_repro_3(data: dict) -> dict:
        res = []
        hg_data_3 = []
        breeds_3_data = []

        for i in data['data']:
            if i['id_type_of_reproduction'] == 3:
                if i['id_breed'] not in breeds_3_data:
                    breeds_3_data.append(i['id_breed'])
                hg_data_3.append(i['to0_2'])
                hg_data_3.append(i['from0_21To0_5'])
                hg_data_3.append(i['from0_6To1_0'])
                hg_data_3.append(i['from1_1to1_5'])
                hg_data_3.append(i['from1_5'])
                res.append(i)

        # print(res)
        data.update({"repro_3": res, "hg_data_3": hg_data_3, "breeds_3_data": breeds_3_data})
        return data

    from_context_get_repro_1(data)
    from_context_get_repro_2(data)
    from_context_get_repro_3(data)

    # return data


# def form_docx_listregion(context: dict):
#     data = {}
#     doc = DocxTemplate(os.path.abspath(f"{BASE_DIR}/media/lst_template_1.docx"))
#     filepath = os.path.abspath(f"{BASE_DIR}/media/list_region/list_region_{32}.docx")
#     get_repro(context)
#     # print(f"REPRO 1 {len(context['repro_1'])}")
#     # print(len(context['repro_3']))
#     context.update({"len_r1": len(context["repro_1"]), "len_r2": len(context['repro_2']),
#                     "len_r3": len(context['repro_3'])})
#     # for i in context['repro_3']:
#     #     print(i['count_of_plants'])
#     # print(context)
#     print(context['repro_1'])
#     print(len(context["repro_3"]))
#     print(context['len_r1'])
#     doc.render(context)
#     doc.save(filepath)
#     # doc.render(repro_1)
#     # doc.save(filepath)
#     return filepath.split("testDjangosite")[1]

def get_all_breeds(data: dict):
    res = []
    for i in data['data']:
        res.append(i['id_breed'])

    return res


def get_all_max_height(data: dict):
    res = []
    for i in data['data']:
        res.append(i['max_height'])

    return res


def get_max_row(data: list):
    from itertools import groupby
    print(data)
    group = groupby(data)
    max_ = max(group, key=lambda k: len(list(k[1])))
    print(max_[0])
    # print(max(group, key=lambda k: len(list(k[1]))))

    return max_[0]


def get_cur_breed(repro_1: dict) -> list[int]:
    res = []
    print(repro_1)
    # for i in repro_1:
        # print(i)
        # print(i['to0_2'])
        # res.append(i['to0_2'])
        # res.append(i['from0_21To0_5'])
        # res.append(i['from0_6To1_0'])
        # res.append(i['from1_1to1_5'])
        # res.append(i['from1_5'])

    res.append(repro_1['to0_2'])
    res.append(repro_1['from0_21To0_5'])
    res.append(repro_1['from0_6To1_0'])
    res.append(repro_1['from1_1to1_5'])
    res.append(repro_1['from1_5'])



    return res


def form_docx_listregion(context: dict):
    trees_row = 1
    ids_list = []
    get_repro(context)
    head_len = len(context['repro_1']) + len(context['repro_2']) + len(context['repro_3'])
    print(head_len)
    all_breeds = []
    data = {"head": ["Искусственное восстановление", "Естесственное возобновление(семенное)",
                     "Естесственное возобновление(вегатативное)"],
            "head_len": head_len,
            "breeds": "Порода: ",
            "hg": "Макс. высота: ",
            "hg_names": ['До 0,2', '0,21-0,5', '0,6-1,0', '1,1-1,5', 'Более 1,5'],
            # "data_breeds_id": [1,2,3,4,5,6,7,8,9]
            }
    breeds = get_all_breeds(context)
    hg = get_all_max_height(context)
    data.update({"data_breeds": breeds, "max_height": hg})
    doc = DocxTemplate(os.path.abspath(f"{BASE_DIR}/media/lst_template_1.docx"))
    filepath = os.path.abspath(f"{BASE_DIR}/media/list_region/list_region_{context['id']}.docx")
    doc.render(context)
    doc.save(filepath)
    doc = Document(filepath)
    # section = doc.sections[-1]
    # section.orientation = 'LANDSCAPE'
    res_len = len(context['breeds_1_data']) + len(context['breeds_2_data']) + len(context['breeds_3_data'])
    table = doc.add_table(1, res_len)
    table.style = 'Table Grid'
    head_row = table.rows[0].cells
    head_row[0].text = data['head'][0]
    head_row[len(context['breeds_1_data'])].text = data['head'][1]
    head_row[len(context['breeds_1_data']) + len(context['breeds_2_data'])].text = data['head'][2]
    print(f"BREEDS_1_DATA {context['breeds_1_data']}")
    # head_row[len(context['repro_1'])].text = data['head'][1]
    # head_row[len(context['repro_1']) + len(context['repro_2'])].text = data['head'][2]
    ## Добавляем 2 новых строки, для породы и максимум
    table.add_row()
    table.add_row()
    breeds_cells = table.rows[1].cells
    hg_cells = table.rows[2].cells
    for i in range(res_len):
        hg_cells[i].text = data['hg'] + str(data['max_height'][i])
    # sum_breeeds - все id-шники деревьев без повторений
    sum_breeds = context['breeds_1_data'] + context['breeds_2_data'] + context['breeds_3_data']
    # выводим айдишники деревьев
    for i in range(len(sum_breeds)):
        breeds_cells[i].text = data['breeds'] + str(sum_breeds[i])

    # # Создание таблицы где от 0,2 и т.д.
    new_table = doc.add_table(1, res_len * 5)
    new_table.style = 'Table Grid'
    new_table.add_row()
    hg_names_row = new_table.rows[0].cells
    first_trees = new_table.rows[trees_row].cells
    print(f"HG DATA 3 {context['breeds_3_data']}")
    j = 0
    for i in range(len(hg_names_row)):
        if j == len(data['hg_names']):
            j = 0
        while i != len(hg_names_row):
            hg_names_row[i].text = data['hg_names'][j]
            hg_names_row[i].paragraphs[0].runs[0].font.size = Pt(8)
            j += 1
            break
    repro1_breeds = data['data_breeds'][0:len(context['repro_1'])]
    max_row_table_2 = get_max_row(data['data_breeds'])
    for i in range(3):
        new_table.add_row()
    # print(repro1_breeds)
    # Сделать функцию которая считает максимальное возможно количество строк которые нужно создать в этой таблице исходя из количества
    # дублирующихся элементов для каждого repro, после чего сравнить количество элементов для каждого repro и на основе этого
    # создать соотвествующее количество строк
    # далее, в цикле ниже есть идея использовать некий счеткик для подсчета текущий строки, дабы можно было прыгать по этим строкам
    # либо прям в цикле создавать и заполнять нееобходимые строки.
    cur_row = 1
    id_breed = 0
    last_index = 0
    cur_index = 0
    print(context['hg_data_1'])
    for i in range(len(new_table.rows[-1].cells)):
        new_table.rows[-1].cells[i].text = "0"
    # сделать чт обы в зависимости от шага записывало данные в соответ. столбец
    step = 5
    cur_breeds = []
    cur_breeds_dict = {}
    AGAIN = False
    for i in range(len(context['repro_1'])):
        cur_row =1
        # cur_index = 0
        # print(context['repro_1'][i]['id_breed'])
        if context['repro_1'][i]['id_breed'] in cur_breeds:
            cur_row += cur_breeds.count(context['repro_1'][i]['id_breed'])
            # cur_index = cur_breeds.index(context['repro_1'][i]['id_breed'])*5
            # cur_index = ''.join(cur_breeds).rindex(context['repro_1'][i]['id_breed'])
            cur_index = max(idx for idx, val in enumerate(cur_breeds) if val == context['repro_1'][i]['id_breed']) * 5
            print(f"BREED {context['repro_1'][i]['id_breed']}")
            print(f"INDEX {cur_index}")
            print(f"CUR_BREED {cur_breeds}")
            cur_breeds.append(context['repro_1'][i]['id_breed'])
            # cur_breeds_dict.update({context['repro_1'][i]['id_breed']: i-1})
            AGAIN = True
        else:
            cur_breeds.append(context['repro_1'][i]['id_breed'])
            cur_breeds_dict[context['repro_1'][i]['id_breed']] = i
        # print(context['repro_1'][i]['count_of_plants'])
        res = get_cur_breed(context['repro_1'][i])
        for j in range(len(res)):
            if AGAIN:
                pos = cur_breeds_dict[context['repro_1'][i]['id_breed']] * 5
                first_trees = new_table.rows[cur_row].cells[pos:cur_index+step]
                # new_table.rows[-1].cells[pos].text += str(int(new_table.rows[-1].cells[pos].text)+int(res[j]))
                print(res[j])
                # new_table_total.rows[0].cells[cur_breeds_dict[context['repro_1'][i]['id_breed']]].text = str(int(new_table_total.rows[0].cells[cur_breeds_dict[context['repro_1'][i]['id_breed']]].text) +res[j])
            else:
                first_trees = new_table.rows[cur_row].cells[cur_index:cur_index + step]
                # new_table.rows[-1].cells[cur_index].text += str(int(new_table.rows[-1].cells[cur_index].text)+int(res[j]))
            first_trees[j].text = str(res[j])
            # new_table.rows[-1].cells[j].text += str(res[j])
        cur_index+= 5
        print(f"CUR INDEX {cur_index}")
    cur_breeds.clear()
    cur_breeds_dict.clear()
    AGAIN = False
    cur_index = len(context['breeds_1_data']) * 5
    cur_index_1 = cur_index
    saved_index = cur_index
    for i in range(len(context['repro_2'])):
        AGAIN = False
        cur_row = 1
        if context['repro_2'][i]['id_breed'] in cur_breeds:
            cur_row += cur_breeds.count(context['repro_2'][i]['id_breed'])
            # cur_index = cur_breeds.index(context['repro_1'][i]['id_breed'])*5
            # cur_index = ''.join(cur_breeds).rindex(context['repro_1'][i]['id_breed'])
            cur_index = max(idx for idx, val in enumerate(cur_breeds) if val == context['repro_2'][i]['id_breed']) * 5
            print(f"BREED {context['repro_2'][i]['id_breed']}")
            print(f"INDEX {cur_index}")
            print(f"CUR_BREED {cur_breeds}")
            cur_breeds.append(context['repro_2'][i]['id_breed'])
            # cur_breeds_dict.update({context['repro_2'][i]['id_breed']: i})
            AGAIN = True
        else:
            cur_breeds.append(context['repro_2'][i]['id_breed'])
            cur_breeds_dict[context['repro_2'][i]['id_breed']] = i
        res = get_cur_breed(context['repro_2'][i])
        for j in range(len(res)):
            if AGAIN:
                pos = ((len(context['breeds_1_data'])*5)) + cur_breeds_dict[context['repro_2'][i]['id_breed']] * 5
                first_trees = new_table.rows[cur_row].cells[pos:len(context['breeds_1_data']) * 5 + cur_index + step]
            else:
                first_trees = new_table.rows[cur_row].cells[cur_index_1:len(context['breeds_1_data'])* 5 + cur_index + step + 5]
            first_trees[j].text = str(res[j])
        if cur_index != 0:
            cur_index_1 += 5
        cur_index = 0

    cur_breeds.clear()
    cur_breeds_dict.clear()
    AGAIN = False
    cur_index = (len(context['breeds_1_data']) + len(context['breeds_2_data'])) * 5
    cur_index_1 = cur_index
    saved_index = cur_index

    step = 10
    for i in range(len(context['repro_3'])):
        AGAIN = False
        cur_row = 1
        if context['repro_3'][i]['id_breed'] in cur_breeds:
            cur_row += cur_breeds.count(context['repro_3'][i]['id_breed'])
            # cur_index = cur_breeds.index(context['repro_1'][i]['id_breed'])*5
            # cur_index = ''.join(cur_breeds).rindex(context['repro_1'][i]['id_breed'])
            cur_index = max(idx for idx, val in enumerate(cur_breeds) if val == context['repro_3'][i]['id_breed']) * 5
            print(f"BREED {context['repro_3'][i]['id_breed']}")
            print(f"INDEX {cur_index}")
            print(f"CUR_BREED {cur_breeds}")
            cur_breeds.append(context['repro_3'][i]['id_breed'])
            # cur_breeds_dict.update({context['repro_2'][i]['id_breed']: i})
            AGAIN = True
        else:
            cur_breeds.append(context['repro_3'][i]['id_breed'])
            cur_breeds_dict[context['repro_3'][i]['id_breed']] = i
        res = get_cur_breed(context['repro_3'][i])
        for j in range(len(res)):
            if AGAIN:
                pos = saved_index + cur_breeds_dict[context['repro_3'][i]['id_breed']] * 5
                print(f"POOOOOOOOOOOOOOOOOOOOOOS {pos}")
                first_trees = new_table.rows[cur_row].cells[pos:]
                # (len(context['breeds_1_data']) + len(context['breeds_2_data']) * 5) + cur_index + step
            else:
                first_trees = new_table.rows[cur_row].cells[
                              cur_index_1:]
                # len(context['breeds_1_data']) * 5 + cur_index + step + 5
            first_trees[j].text = str(res[j])
        if cur_index != 0:
            cur_index_1 += 5
        cur_index = 0

    for i in range(0, len(new_table.columns)):
        # print(new_table.columns[i].cells[2].text)
        for j in range(1, len(new_table.rows)):
            if new_table.columns[i].cells[j].text == '':
                break
            new_table.rows[-1].cells[i].text = str(int(new_table.rows[-1].cells[i].text) +int(new_table.columns[i].cells[j].text))
    # new_table_total.add_row()
    # total of every breeds new table

    new_table_total = doc.add_table(1, res_len)
    new_table_total.style = 'Table Grid'
    new_ga = doc.add_table(1, res_len)
    new_ga.style = 'Table Grid'
    for i in range(res_len):
        new_table_total.rows[0].cells[i].text = "0"
    now = 0
    for i in range(res_len):
        for j in range(now, now+5):
            new_table_total.rows[0].cells[i].text = str(int(new_table_total.rows[0].cells[i].text)+ int(new_table.rows[-1].cells[j].text))
        if context['count_sample_area'] > 1:
            new_ga.rows[0].cells[i].text = str(round(int(new_table_total.rows[0].cells[i].text)/context['sample_area']))
        else:
            new_ga.rows[0].cells[i].text = str(
                round(int(new_table_total.rows[0].cells[i].text) / context['square_one_sample_area'], 2))
        now+=5
        # Сюда добавить условие на счет 1/несколько пп, пока сделаю так




    filepath = os.path.abspath(f"{BASE_DIR}/media/list_region/list_region_{context['id']}.docx")
    doc.save(filepath)
    return filepath.split("testDjangosite")[1]


test_data = {
    "id": 22,
    "forestly": "Тестовое лесничество",
    "district_forestly": "тестовое участковое лесничество",
    "soil_lot": 21,
    "dacha": "тестовая дача",
    "sample_area": 5,
    "name_quarter": "тестовый квартал",
    "square_one_sample_area": 480,

    "data": [
        {
            "avg_diameter": 0.0,
            "avg_height": 0.1,
            "avg_height_undergrowth": 0.0,
            "count_of_plants": 4,
            "from0_21To0_5": 3,
            "from0_6To1_0": 4,
            "from1_1to1_5": 5,
            "from1_5": 6,
            "id": 445,
            "id_breed": 1,
            "id_sample": 368,
            "id_type_of_reproduction": 1,
            "main": 0,
            "mark_update": 1,
            "max_height": 32.4,
            "to0_2": 4
        },
        {
            "avg_diameter": 0.0,
            "avg_height": 0.8,
            "avg_height_undergrowth": 0.0,
            "count_of_plants": 9,
            "from0_21To0_5": 55,
            "from0_6To1_0": 9,
            "from1_1to1_5": 77,
            "from1_5": 13,
            "id": 451,
            "id_breed":2,
            "id_sample": 368,
            "id_type_of_reproduction": 1,
            "main": 0,
            "mark_update": 2,
            "max_height":66,
            "to0_2": 22
        },
        {
            "avg_diameter": 0.0,
            "avg_height": 0.0,
            "avg_height_undergrowth": 0.0,
            "count_of_plants": 15,
            "from0_21To0_5": 54,
            "from0_6To1_0": 32,
            "from1_1to1_5": 11,
            "from1_5": 66,
            "id": 448,
            "id_breed": 1,
            "id_sample": 368,
            "id_type_of_reproduction": 1,
            "main": 0,
            "mark_update": 0,
            "max_height": 12,
            "to0_2": 99
        },
        {
            "avg_diameter": 0.0,
            "avg_height": 0.15,
            "avg_height_undergrowth": 0.0,
            "count_of_plants": 1,
            "from0_21To0_5": 33,
            "from0_6To1_0": 11,
            "from1_1to1_5": 22,
            "from1_5": 88,
            "id": 446,
            "id_breed": 1,
            "id_sample": 368,
            "id_type_of_reproduction": 2,
            "main": 0,
            "mark_update": 1,
            "max_height": 77,
            "to0_2":100
        },
        {
            "avg_diameter": 0.0,
            "avg_height": 0.3,
            "avg_height_undergrowth": 0.0,
            "count_of_plants": 7,
            "from0_21To0_5": 6,
            "from0_6To1_0": 1,
            "from1_1to1_5": 22222,
            "from1_5": 0,
            "id": 447,
            "id_breed": 5,
            "id_sample": 368,
            "id_type_of_reproduction": 3,
            "main": 0,
            "mark_update": 1,
            "max_height": 14,
            "to0_2": 10000
        },
        {
            "avg_diameter": 0.0,
            "avg_height": 0.0,
            "avg_height_undergrowth": 0.0,
            "count_of_plants": 5,
            "from0_21To0_5":2,
            "from0_6To1_0": 44,
            "from1_1to1_5": 11,
            "from1_5": 0,
            "id": 449,
            "id_breed": 6,
            "id_sample": 368,
            "id_type_of_reproduction": 2,
            "main": 0,
            "mark_update": 0,
            "max_height":15,
            "to0_2": 4123
        },
        {
            "avg_diameter": 0.0,
            "avg_height": 0.8,
            "avg_height_undergrowth": 0.0,
            "count_of_plants": 6,
            "from0_21To0_5": 54,
            "from0_6To1_0": 6,
            "from1_1to1_5": 10,
            "from1_5": 0,
            "id": 450,
            "id_breed": 6,
            "id_sample": 368,
            "id_type_of_reproduction": 3,
            "main": 0,
            "mark_update": 0,
            "max_height": 16,
            "to0_2": 2000
        },
        {
            "avg_diameter": 0.0,
            "avg_height": 0.0,
            "avg_height_undergrowth": 0.0,
            "count_of_plants":55,
            "from0_21To0_5": 44,
            "from0_6To1_0": 887,
            "from1_1to1_5": 445,
            "from1_5": 33,
            "id": 452,
            "id_breed": 6,
            "id_sample": 368,
            "id_type_of_reproduction": 2,
            "main": 0,
            "mark_update": 2,
            "max_height": 17,
            "to0_2": 421
        },
        {
            "avg_diameter": 0.0,
            "avg_height": 0.0,
            "avg_height_undergrowth": 0.0,
            "count_of_plants": 0,
            "from0_21To0_5": 12,
            "from0_6To1_0": 13,
            "from1_1to1_5": 14,
            "from1_5": 33,
            "id": 453,
            "id_breed": 6,
            "id_sample": 368,
            "id_type_of_reproduction": 3,
            "main": 0,
            "mark_update": 2,
            "max_height":18,
            "to0_2": 50000
        }
    ],
    "id_sample": 368
}

# form_docx_listregion(test_data)
