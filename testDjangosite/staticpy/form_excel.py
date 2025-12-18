from distutils.fancy_getopt import wrap_text
from staticpy.form_excel_draw_list3 import main_draw
from staticpy.excel_desc_region import create_plot_description_excel
from staticpy.excel_form_field import create_forest_survey_excel
import openpyxl
from openpyxl.styles import PatternFill, Alignment, Font, Border, Side
from openpyxl.workbook import Workbook

from testDjangosite.settings import BASE_DIR

# def merge_and_name(sheets, cur_col, name):
#     sheets.merge_cells(start_row=6, start_column=cur_col, end_row=6, end_column=cur_col)
#     cur_col = last_col
#     sheets.cell(row=6, column=last_col, value="Естественное возобновление (вегетативное)")

def set_border(ws, cell_range):
    thin_border = Border(left=Side(style='thin'),
                         right=Side(style='thin'),
                         top=Side(style='thin'),
                         bottom=Side(style='thin'))
    for row in ws[cell_range]:
        for cell in row:
            cell.border = thin_border

def len_samples(data: dict):
    data_of_samples = []
    for i in data['data']:
        data_of_samples.append(i['id_sample'])

    return len(data_of_samples)

def create_header(sheets, data):

    # sheets = ws.active


    sheets['B2'].value = "Перечетная ведомость участка №"
    sheets['B2'].border = Border(top=Side(style="thin"),bottom=Side(style="thin"))
    sheets['A2'].border = Border(top=Side(style="thin"),bottom=Side(style="thin"))
    sheets['A2'].border = Border(top=Side(style="thin"),bottom=Side(style="thin"))
    sheets.merge_cells("B2:F2")
    sheets['G2'].value = data['number_region']
    sheets['G2'].border = Border(top=Side(style="thin"),bottom=Side(style="thin"))
    sheets['H2'].border = Border(top=Side(style="thin"),bottom=Side(style="thin"))
    sheets['I2'].border = Border(top=Side(style="thin"),bottom=Side(style="thin"))
    sheets['J2'].border = Border(top=Side(style="thin"),bottom=Side(style="thin"))
    sheets['K2'].border = Border(top=Side(style="thin"),bottom=Side(style="thin"))
    sheets['L2'].border = Border(top=Side(style="thin"), bottom=Side(style="thin"), right=Side(style="thin"),)

    sheets['A3'].value = "Лесничество"
    sheets['C3'].value = data['name_forestly']
    sheets['C3'].border = Border(bottom=Side(style="thin"))
    sheets.merge_cells("A3:B3")
    sheets.merge_cells("C3:D3")

    sheets['E3'].value = "Участковое лесничество"
    sheets['G3'].value = data['name_district_forestly']
    sheets['G3'].border = Border(bottom=Side(style="thin"))
    sheets.merge_cells("E3:F3")
    sheets.merge_cells("G3:H3")

    sheets['I3'].value = "Урочище(дача)"
    sheets['K3'].value = data['dacha']
    sheets['K3'].border = Border(bottom=Side(style="thin"))
    sheets.merge_cells("I3:J3")
    sheets.merge_cells("K3:L3")

    sheets['A4'].value = 'Квартал'
    sheets['C4'].value = data['name_quarter']
    sheets['C4'].border = Border(bottom=Side(style="thin"))
    sheets.merge_cells("A4:B4")
    sheets.merge_cells("C4:D4")

    sheets['E4'].value = "Выдел"
    sheets['G4'].value = data['soil_lot']
    sheets['G4'].border = Border(bottom=Side(style="thin"))
    sheets.merge_cells("E4:F4")
    sheets.merge_cells("G4:H4")

    sheets['I4'].value = "Площадь га."
    sheets['K4'].value = data['sample_region']
    sheets['K4'].border = Border(bottom=Side(style="thin"))
    sheets.merge_cells("I4:J4")
    sheets.merge_cells("K4:L4")

    sheets['A5'].value = "кол-во ПП, шт"

    len_samples_res = 1
    if data['count_sample_area'] != None:
        sheets['C5'].value = data['count_sample_area']
    else:
        len_samples_res = len_samples(data)
        sheets['C5'].value = len_samples_res

    sheets['C5'].border = Border(bottom=Side(style="thin"))
    sheets.merge_cells("A5:B5")
    sheets.merge_cells("C5:D5")

    sheets['E5'].value = "площадь 1 ПП"
    sheets['G5'].value = data['square']
    sheets['G5'].border = Border(bottom=Side(style="thin"))
    sheets.merge_cells("E5:F5")
    sheets.merge_cells("G5:H5")

    sheets['I5'].value = "площадь ПП, га."
    if data['count_sample_area'] is None or data['count_sample_area'] == 0:
        data['count_sample_area'] = 1
    if data['square'] is None or data['square'] == 0:
        data['square'] = 1
    try:
        sheets['K5'].value = (data['square'] / data['count_sample_area']) / 10000
    except:
        if len_samples_res == 0:
            len_samples_res = 1
        sheets['K5'].value = (data['square']/len_samples_res)/10000
    sheets['K5'].border = Border(bottom=Side(style="thin"))
    sheets.merge_cells("I5:J5")
    sheets.merge_cells("K5:L5")

    set_border(sheets, "A3:L5")





def undergrowth_excel(sheets, data: dict = None):
    # wb = Workbook()
    # sheets = wb.active
    # sheets.title = "Подлесок"
    #
    # for row in sheets.iter_rows():
    #     for cell in row:
    #         cell.style.alignment = Alignment(wrap_text=True)

    # create_header(sheets, data)


    sheets['A7'].value = "Подлесок"
    sheets.merge_cells("A7:C7")
    sheets['A7'].alignment = Alignment(horizontal='center')
    sheets['A8'].value = "Порода"
    sheets['B8'].value = "Количество растений"
    sheets['B8'].alignment = Alignment(wrap_text=True)
    sheets['C8'].value = "Средняя высота."

    sheets.column_dimensions['A'].width = 15
    sheets.column_dimensions['B'].width = 15
    sheets.column_dimensions['C'].width = 20
    sheets.column_dimensions['D'].width = 20
    # sheets.column_dimensions['E'].width = 15
    # sheets.column_dimensions['F'].width = 15
    # sheets.column_dimensions['G'].width = 15
    # sheets.column_dimensions['H'].width = 15
    # sheets.column_dimensions['I'].width = 15

    cur_row = 8
    cur_col = 1
    all_count_sum = 0
    last_hg = 0
    last_count = 0
    avg_hg = 0
    name_breed = ''
    for i in data['data']:
        if i['id_undergrowth'] == None:
            continue
        if cur_col == 1:
            for j in data['name_breeds_under']:
                if j['id'] == i['id_undergrowth']:
                    name_breed = j['name']
            cell = sheets.cell(row=cur_row, column=cur_col, value=name_breed)
            # cell.alignment = Alignment(wrap_text=True)
            # cell.border = thin_border
            cur_col+= 1
        if cur_col == 2:
            cell = sheets.cell(row=cur_row, column=cur_col, value=i['count_of_plants'])
            all_count_sum+=i['count_of_plants']
            # cell.alignment = Alignment(wrap_text=True)
            # cell.border = thin_border
            last_count = i['count_of_plants']
            cur_col+=1
        if cur_col == 3:
            cell = sheets.cell(row=cur_row, column=cur_col, value=i['avg_height_undergrowth'])
            last_hg = i['avg_height_undergrowth']
            avg_hg += last_count+last_hg
            # cell.alignment = Alignment(wrap_text=True)
            # cell.border = thin_border
            cur_col = 1
            cur_row+=1
    # if all_count_sum == 0:
    #     all_count_sum = 1
    sheets.cell(row=cur_row, column=1, value="Итого")
    sheets.cell(row=cur_row, column=2, value=all_count_sum)
    sheets.cell(row=cur_row, column=3, value= avg_hg/all_count_sum if all_count_sum > 0 else 0)
    sheets.cell(row=cur_row+1, column=1, value="На 1 га")
    sheets.cell(row=cur_row+1, column=2, value=all_count_sum/10000 if all_count_sum > 0 else 0)
    sheets.cell(row=cur_row+1, column=3, value=avg_hg/all_count_sum if all_count_sum > 0 else 0)


    test = sheets.cell(row=cur_row+1, column=3)
    set_border(sheets, f"A7:{test.coordinate}")


    # print(data)
    # wb.save(f'{BASE_DIR}/media/excel_files/listregion/undergrowth_{data["id_list_region"]}.xlsx')
# type_repro: [
    #     {
    #         "1":
    #             "id_breed": [{
    #                   "id_sample": lists of this sample},
    #                   {"id_sample": lists of this sample
    # }]
    #         "2": ....
    #     }
    # ]
def calculate_total(lst):
    res = 0
    res+= lst['to0_2']
    res+= lst['from0_21To0_5']
    res+= lst['from0_6To1_0']
    res+= lst['from1_1to1_5']
    res+= lst['from1_5']


    return res


def calculate_each(data:dict):
    var_1 = 0
    var_2 = 0
    var_3 = 0
    var_4 = 0
    var_5 = 0

    for sample in data:
        for i in data[sample]:
            var_1 += i['to0_2']
            var_2 += i['from0_21To0_5']
            var_3 += i['from0_6To1_0']
            var_4 += i['from1_1to1_5']
            var_5 += i['from1_5']

    return var_1, var_2, var_3, var_4, var_5


def find_max(data:dict, sample_list):
    hg = 0

    for sample in sample_list:
        for i in data[sample]:
            if i['max_height'] > hg:
                hg = i['max_height']

    return hg

def get_repro(data: dict) -> None:
    data['hg_each'] = []
    def from_context_get_repro_1(data: dict) -> dict:
        breeds_data = []
        breeds_dict = {}
        sample_data = []
        sample_dict = {}
        list_data = []
        total = 0
        breed_total = {}
        hg_each = []
        max_hg = []

        for i in data['data']:
            if i['id_type_of_reproduction'] == 1:
                if i['id_breed'] not in breeds_data and i['id_breed'] != None:
                    breeds_data.append(i['id_breed'])
                if i['id_sample'] not in sample_data:
                    sample_data.append(i['id_sample'])

        for i in breeds_data:
            for j in sample_data:
                for k in data['data']:
                    if k['id_type_of_reproduction'] == 1 and k['id_breed'] == i and k['id_sample'] == j:
                        list_data.append(k)
                        total+= calculate_total(k)
                        # calculate_total(k)
                # sample_dict[j].append(list_data.copy())
                sample_dict[j] = list_data.copy()
                list_data.clear()
            # breeds_dict[i].append(sample_dict.copy())
            breeds_dict[i] = sample_dict.copy()
            hg_each.extend(calculate_each(breeds_dict[i]))
            max_hg.append(find_max(breeds_dict[i], sample_data))
            # breeds_dict[i]['total'] = total
            breed_total[i] = total
            sample_dict.clear()
            total = 0
        res = {}
        res['breeds_dict'] = breeds_dict
        res['breeds_data'] = breeds_data
        res['sample_data'] = sample_data
        data['breed_total_1'] = breed_total
        data['hg_each'].extend(hg_each)
        data['max_hg_1'] = max_hg
        # data.update({"repro_1": breeds_dict, "breeds": breeds_data, "sample_data": sample_data})
        data.update({"repro_1": res})
        return data

    def from_context_get_repro_2(data: dict) -> dict:
        breeds_data = []
        breeds_dict = {}
        sample_data = []
        sample_dict = {}
        list_data = []
        total = 0
        breed_total = {}
        hg_each = []
        max_hg = []

        for i in data['data']:
            if i['id_type_of_reproduction'] == 2:
                if i['id_breed'] not in breeds_data and i['id_breed'] != None:
                    breeds_data.append(i['id_breed'])
                if i['id_sample'] not in sample_data:
                    sample_data.append(i['id_sample'])

        for i in breeds_data:
            for j in sample_data:
                for k in data['data']:
                    if k['id_type_of_reproduction'] == 2 and k['id_breed'] == i and k['id_sample'] == j:
                        list_data.append(k)
                        total += calculate_total(k)
                        # calculate_total(k)
                # sample_dict[j].append(list_data.copy())
                sample_dict[j] = list_data.copy()
                list_data.clear()
            # breeds_dict[i].append(sample_dict.copy())
            breeds_dict[i] = sample_dict.copy()
            hg_each.extend(calculate_each(breeds_dict[i]))
            max_hg.append(find_max(breeds_dict[i], sample_data))
            # breeds_dict[i]['total'] = total
            breed_total[i] = total
            sample_dict.clear()
            total = 0
        res = {}
        res['breeds_dict'] = breeds_dict
        res['breeds_data'] = breeds_data
        res['sample_data'] = sample_data
        data['breed_total_2'] = breed_total
        data['hg_each'].extend(hg_each)
        data['max_hg_2'] = max_hg
        data.update({"repro_2": res})
        return data

    def from_context_get_repro_3(data: dict) -> dict:
        breeds_data = []
        breeds_dict = {}
        sample_data = []
        sample_dict = {}
        list_data = []
        total = 0
        breed_total = {}
        hg_each = []
        max_hg = []

        for i in data['data']:
            if i['id_type_of_reproduction'] == 3:
                if i['id_breed'] not in breeds_data and i['id_breed'] != None:
                    breeds_data.append(i['id_breed'])
                if i['id_sample'] not in sample_data:
                    sample_data.append(i['id_sample'])

        for i in breeds_data:
            for j in sample_data:
                for k in data['data']:
                    if k['id_type_of_reproduction'] == 3 and k['id_breed'] == i and k['id_sample'] == j:
                        list_data.append(k)
                        total += calculate_total(k)
                        # calculate_total(k)
                # sample_dict[j].append(list_data.copy())
                sample_dict[j] = list_data.copy()
                list_data.clear()
            # breeds_dict[i].append(sample_dict.copy())
            breeds_dict[i] = sample_dict.copy()
            hg_each.extend(calculate_each(breeds_dict[i]))
            max_hg.append(find_max(breeds_dict[i], sample_data))
            # breeds_dict[i]['total'] = total
            breed_total[i] = total
            sample_dict.clear()
            total = 0
        res = {}
        res['breeds_dict'] = breeds_dict
        res['breeds_data'] = breeds_data
        res['sample_data'] = sample_data
        data['breed_total_3'] = breed_total
        data['hg_each'].extend(hg_each)
        data['max_hg_3'] = max_hg
        data.update({"repro_3": res})
        return data


    from_context_get_repro_1(data)
    from_context_get_repro_2(data)
    from_context_get_repro_3(data)

def count_breeds(data):
    res = []
    to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5 = 0, 0, 0, 0, 0
    for i in data:
        to0_2 += i['to0_2']
        from0_21To0_5 += i['from0_21To0_5']
        from0_6To1_0 += i['from0_6To1_0']
        from1_1to1_5 += i['from1_1to1_5']
        from1_5 += i['from1_5']

    res.extend((to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5))
    return res


def draw_col(sheets, start_row, start_col, data, samples_row, breed, hg, FLAG = True, name_col = None):
    # sheets.merge_cells(start_row=6, start_column=start_col, end_row=6, end_column=start_col + 5)
    def_row = 7
    sheets.cell(row=def_row+ 1, column=start_col).value = "Порода"
    sheets.cell(row=def_row+ 1, column=start_col+1).value = breed
    sheets.cell(row=def_row+ 1, column=start_col+1).alignment= Alignment(wrap_text=True)
    sheets.merge_cells(start_row=def_row + 1, start_column=start_col+1, end_row=def_row + 1, end_column=start_col + 4)

    sheets.cell(row=def_row+ 2, column=start_col).value = "Макс высота"
    sheets.cell(row=def_row+ 2, column=start_col).alignment= Alignment(wrap_text=True)
    sheets.cell(row=def_row+ 2, column=start_col+1).value = hg
    sheets.merge_cells(start_row=def_row + 2, start_column=start_col+1, end_row=def_row + 2, end_column=start_col + 4)

    sheets.cell(row=def_row+ 3, column=start_col).value = "Высота растений, м."
    sheets.merge_cells(start_row=def_row + 3, start_column=start_col, end_row=def_row + 3, end_column=start_col + 4)

    last_row = def_row + 3
    last_col = start_col+4
    max_row = 0
    total = 0
    hg_names = ['До 0,2', '0,21-0,5', '0,6-1,0', '1,1-1,5', 'Более 1,5']
    for col in range(5):
        for row in range(1):
            sheets.cell(row=last_row + 1, column=start_col + col, value=hg_names[col])
    last_row += 2
    for i in data:
        res = count_breeds(data[i])
        if i not in samples_row and FLAG:
            if len(samples_row) == 0:
                samples_row[i] = last_row
            else:
                last_row = max(samples_row.values())+1
                samples_row[i] = last_row
        for j in range(len(res)):
            sheets.cell(row=last_row,column=start_col+j, value=res[j])
            total+=0
        samples_row[i] = last_row
        sheets.cell(row=last_row, column=1, value=i)
        last_row+=1

    # print(data[368])
    return last_row, last_col+1

def draw_empty_col(sheets, start_col, ):
    # sheets.merge_cells(start_row=6, start_column=start_col, end_row=6, end_column=start_col + 5)
    def_row = 7
    sheets.cell(row=def_row + 1, column=start_col).value = "Порода"
    # sheets.cell(row=def_row + 1, column=start_col + 1).value = breed
    sheets.cell(row=def_row + 1, column=start_col + 1).alignment = Alignment(wrap_text=True)
    sheets.merge_cells(start_row=def_row + 1, start_column=start_col + 1, end_row=def_row + 1, end_column=start_col + 4)

    sheets.cell(row=def_row + 2, column=start_col).value = "Макс высота"
    sheets.cell(row=def_row + 2, column=start_col).alignment = Alignment(wrap_text=True)
    # sheets.cell(row=def_row + 2, column=start_col + 1).value = hg
    sheets.merge_cells(start_row=def_row + 2, start_column=start_col + 1, end_row=def_row + 2, end_column=start_col + 4)

    sheets.cell(row=def_row + 3, column=start_col).value = "Высота растений, м."
    sheets.merge_cells(start_row=def_row + 3, start_column=start_col, end_row=def_row + 3, end_column=start_col + 4)

    last_row = def_row + 3
    last_col = start_col + 4
    max_row = 0
    total = 0
    hg_names = ['До 0,2', '0,21-0,5', '0,6-1,0', '1,1-1,5', 'Более 1,5']
    for col in range(5):
        for row in range(1):
            sheets.cell(row=last_row + 1, column=start_col + col, value=hg_names[col])
    return last_row, last_col + 1

def list_region_excel(data: dict, podlesok: bool = True, others: bool = False):
    from openpyxl.utils.cell import get_column_letter
    sorted(data)
    get_repro(data)


    wb = Workbook()
    # data.sort()
    sheets = wb.active
    sheets.title = "Перечетная ведомость"

    create_header(sheets, data)


    start_col = 2
    start_row = 7

    last_col = start_col
    last_row = start_row
    if len(data['repro_1']['breeds_data']) == 0 and len(data['repro_2']['breeds_data'])== 0 and len(data['repro_3']['breeds_data'])==0:
        if not others:
            new_sheet = wb.create_sheet('Подлесок')
            undergrowth_excel(new_sheet, data=data)
            create_header(new_sheet, data=data)
            filepath = f'{BASE_DIR}/media/excel_files/listregion/listregion_{data["id"]}.xlsx'
        else:
            create_plot_description_excel(data=data['data_for_desc'], wb=wb, save=False)
            create_forest_survey_excel(data=data['data_for_field'], wb=wb, save=False)
            filepath = f'{BASE_DIR}/media/excel_files/listregion/listregion_field_desc{data["id"]}.xlsx'
        wb.save(filepath)
        return filepath.split("testDjangosite")[1]


    sheets['A7'].value = "Номер пробной площади"
    sheets.merge_cells("A7:A10")
    # sheets['A6'].aligment = Alignment(wrap_text=True)
    sheets['B7'].value = "Искусственное восстановление"
    test = sheets['B7']
    true_breeds = {}
    for i in data['name_breeds']:
        true_breeds[i['id']] = i['name_breed']


    samples_row = {}
    step = 0
    if len(data['repro_1']['breeds_data']) == 0:
        last_row, last_col = draw_empty_col(sheets, last_col)
    else:
        for i in data['repro_1']['breeds_data']:
            last_row, last_col = draw_col(sheets, last_row, last_col, data['repro_1']['breeds_dict'][i], samples_row, true_breeds[i], data['max_hg_1'][step],False)
            step+=1
    # set_border(wb, "A6")
    sheets.merge_cells(start_row=start_row, start_column=start_col, end_row=start_row, end_column=last_col-1)


    cur_col = last_col
    sheets.cell(row=start_row, column=last_col, value="Естественное возобновление (семенное)")
    step = 0
    if len(data['repro_2']['breeds_data']) == 0:
        last_row, last_col = draw_empty_col(sheets, last_col)
    else:
        for i in data['repro_2']['breeds_data']:
            last_row, last_col = draw_col(sheets, last_row, last_col, data['repro_2']['breeds_dict'][i], samples_row, true_breeds[i], data['max_hg_2'][step])
            step+=1
    sheets.merge_cells(start_row=start_row, start_column=cur_col, end_row=start_row, end_column=last_col - 1)

    cur_col = last_col
    sheets.cell(row=start_row, column=last_col, value="Естественное возобновление (вегетативное)")
    step = 0
    if len(data['repro_3']['breeds_data']) == 0:
        last_row, last_col = draw_empty_col(sheets, last_col)
    else:
        for i in data['repro_3']['breeds_data']:
            last_row, last_col = draw_col(sheets, last_row, last_col, data['repro_3']['breeds_dict'][i], samples_row, true_breeds[i], data['max_hg_3'][step])
            step += 1
    sheets.merge_cells(start_row=start_row, start_column=cur_col, end_row=start_row, end_column=last_col - 1)
    # if len(samples_row)!=0:
    #     cur_row = max(samples_row.values())+1
    # else:
    #     cur_row = last_row
    cur_row = max(samples_row.values()) + 1

    sheets.cell(row=cur_row, column=1, value="Итого:")
    for i in range(len(data['hg_each'])):
        sheets.cell(row=cur_row, column=i+2, value=data['hg_each'][i])
    sheets.cell(row=cur_row+1, column=1, value="Всего")
    res_total = []
    if len(data['repro_1']['breeds_data']) == 0:
        res_total.append(0)
    else:
        res_total.extend(list(data['breed_total_1'].values()))
    if len(data['repro_2']['breeds_data']) == 0:
        res_total.append(0)
    else:
        res_total.extend(list(data['breed_total_2'].values()))
    if len(data['repro_3']['breeds_data']) == 0:
        res_total.append(0)
    else:
        res_total.extend(list(data['breed_total_3'].values()))


    # print(res_total)

    step = 0
    for i in range(2, last_col, 5):
        sheets.cell(row=cur_row + 1, column=i, value=res_total[step])
        sheets.merge_cells(start_row=cur_row+1, start_column=i, end_row=cur_row+1, end_column=i+4)
        try:
            if data['count_sample_area'] > 1:
                sheets.cell(row=cur_row + 2, column=i).value = round(res_total[step]/data['sample_area'])
            else:
                sheets.cell(row=cur_row + 2, column=i).value = round(res_total[step] / data['square_one_sample_area'], 2)
        except:
            pass
        sheets.merge_cells(start_row=cur_row+2, start_column=i, end_row=cur_row+2, end_column=i+4)
        step+=1

    sheets.cell(row=cur_row+2, column=1, value="на 1 Га")
    len_samples_res = len_samples(data)
    for i in range(2, last_col, 5):

        try:
            cell =  sheets.cell(row=cur_row + 1, column=i)
            res = (data['square'] / len_samples_res) / 10000
            sheets.cell(row=cur_row + 2, column=i, value=cell.value/res)
        except:
            pass
        sheets.merge_cells(start_row=cur_row+2, start_column=i, end_row=cur_row+2, end_column=i+4)
        # sheets.merge_cells(start_row=cur_row+2, start_column=i, end_row=cur_row+2, end_column=i+4)
        step+=1

    last_cell = sheets.cell(row=cur_row+2, column=last_col-1)

    set_border(sheets, f"A7:{last_cell.coordinate}")

    for i in range(7):
        rd = sheets.row_dimensions[i]
        rd.height = 30

    for row in sheets.iter_rows():
        for cell in row:
            cell.alignment = Alignment(wrap_text=True, vertical='top')
    if podlesok :
        new_sheet = wb.create_sheet('Подлесок')
        undergrowth_excel(new_sheet, data=data)
        create_header(new_sheet, data=data)
    filepath = f'{BASE_DIR}/media/excel_files/listregion/listregion_{data["id"]}.xlsx'
    if others:
        create_plot_description_excel(data=data['data_for_desc'], wb=wb, save=False)
        create_forest_survey_excel(data=data['data_for_field'], breeds_data = data, wb=wb, save=False)
        filepath = f'{BASE_DIR}/media/excel_files/listregion/listregion_field_desc{data["id"]}.xlsx'
    wb.save(filepath)
    print(f"FILEPATH {filepath}")
    return filepath.split("testDjangosite")[1]


# list_region_excel()


data = {"data": [
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
        "max_height": 0.0,
        "to0_2": 4
    },
    {
        "avg_diameter": 0.0,
        "avg_height": 0.15,
        "avg_height_undergrowth": 0.0,
        "count_of_plants": 1,
        "from0_21To0_5": 1,
        "from0_6To1_0": 0,
        "from1_1to1_5": 0,
        "from1_5": 0,
        "id": 446,
        "id_breed": 1,
        "id_sample": 380,
        "id_type_of_reproduction": 1,
        "main": 0,
        "mark_update": 1,
        "max_height": 0.0,
        "to0_2": 0
    },
    {
        "avg_diameter": 0.0,
        "avg_height": 0.3,
        "avg_height_undergrowth": 0.0,
        "count_of_plants": 7,
        "from0_21To0_5": 6,
        "from0_6To1_0": 1,
        "from1_1to1_5": 0,
        "from1_5": 0,
        "id": 447,
        "id_breed": 1,
        "id_sample": 368,
        "id_type_of_reproduction": 3,
        "main": 0,
        "mark_update": 1,
        "max_height": 0.0,
        "to0_2": 0
    },
    {
        "avg_diameter": 0.0,
        "avg_height": 0.0,
        "avg_height_undergrowth": 0.0,
        "count_of_plants": 0,
        "from0_21To0_5": 0,
        "from0_6To1_0": 0,
        "from1_1to1_5": 0,
        "from1_5": 0,
        "id": 448,
        "id_breed": 6,
        "id_sample": 368,
        "id_type_of_reproduction": 1,
        "main": 0,
        "mark_update": 0,
        "max_height": 0.0,
        "to0_2": 0
    },
    {
        "avg_diameter": 0.0,
        "avg_height": 0.0,
        "avg_height_undergrowth": 0.0,
        "count_of_plants": 0,
        "from0_21To0_5": 2,
        "from0_6To1_0": 3,
        "from1_1to1_5": 4,
        "from1_5": 4,
        "id": 449,
        "id_breed": 6,
        "id_sample": 368,
        "id_type_of_reproduction": 2,
        "main": 0,
        "mark_update": 0,
        "max_height": 0.0,
        "to0_2": 1
    },
    {
        "avg_diameter": 0.0,
        "avg_height": 0.8,
        "avg_height_undergrowth": 0.0,
        "count_of_plants": 6,
        "from0_21To0_5": 0,
        "from0_6To1_0": 6,
        "from1_1to1_5": 0,
        "from1_5": 0,
        "id": 450,
        "id_breed": 6,
        "id_sample": 368,
        "id_type_of_reproduction": 3,
        "main": 0,
        "mark_update": 0,
        "max_height": 0.0,
        "to0_2": 0
    },
    {
        "avg_diameter": 0.0,
        "avg_height": 0.8,
        "avg_height_undergrowth": 0.0,
        "count_of_plants": 9,
        "from0_21To0_5": 0,
        "from0_6To1_0": 9,
        "from1_1to1_5": 0,
        "from1_5": 0,
        "id": 451,
        "id_breed": 6,
        "id_sample": 368,
        "id_type_of_reproduction": 1,
        "main": 0,
        "mark_update": 2,
        "max_height": 0.0,
        "to0_2": 0
    },
    {
        "avg_diameter": 0.0,
        "avg_height": 0.0,
        "avg_height_undergrowth": 0.0,
        "count_of_plants": 0,
        "from0_21To0_5": 2,
        "from0_6To1_0": 3,
        "from1_1to1_5": 4,
        "from1_5": 12,
        "id": 452,
        "id_breed": 6,
        "id_sample": 390,
        "id_type_of_reproduction": 2,
        "main": 0,
        "mark_update": 2,
        "max_height": 0.0,
        "to0_2": 1
    },
    {
        "avg_diameter": 0.0,
        "avg_height": 0.0,
        "avg_height_undergrowth": 0.0,
        "count_of_plants": 0,
        "from0_21To0_5": 0,
        "from0_6To1_0": 0,
        "from1_1to1_5": 0,
        "from1_5": 0,
        "id": 453,
        "id_breed": 6,
        "id_sample": 368,
        "id_type_of_reproduction": 3,
        "main": 0,
        "mark_update": 2,
        "max_height": 0.0,
        "to0_2": 0
    }
],
    "id_sample": 368
}
# list_region_excel(data)
