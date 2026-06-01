import openpyxl
from openpyxl.styles import Alignment, Border, Side, Font, PatternFill
from collections import defaultdict
from testDjangosite.settings import BASE_DIR
import os


def create_accounting_table_from_json(json_data: dict):
    wb = openpyxl.Workbook()
    ws = wb.active
    ws.title = "Лист1"

    thin_border = Border(left=Side(style='thin'), right=Side(style='thin'), top=Side(style='thin'),
                         bottom=Side(style='thin'))
    center_alignment = Alignment(horizontal='center', vertical='center', wrap_text=True)
    left_alignment = Alignment(horizontal='left', vertical='center', wrap_text=True)
    bold_font = Font(bold=True, name='Times New Roman', size=10)
    normal_font = Font(name='Times New Roman', size=10)

    list_region = json_data.get('list_region', [{}])[0] if json_data.get('list_region') else {}
    output_file = f'{BASE_DIR}/media/excel_files/fc/list_region_{list_region.get("id")}.xlsx'
    samples = json_data.get('samples', [])
    molod = json_data.get('molod', [])
    crops = json_data.get('crops', [])
    plants = json_data.get('plants', [])
    all_breed = {b['id']: b for b in json_data.get('all_breed', [])}

    crops_by_sample_and_breed = defaultdict(list)
    for c in crops:
        key = (c.get('id_sample_id'), c.get('id_breed_id'))
        crops_by_sample_and_breed[key].append(c)

    plants_by_sample_and_breed = defaultdict(list)
    for p in plants:
        key = (p.get('id_sample_id'), p.get('id_breed_id'))
        plants_by_sample_and_breed[key].append(p)

    molod_by_sample_and_breed = defaultdict(list)
    for m in molod:
        key = (m.get('id_sample_id'), m.get('id_breed_id'))
        molod_by_sample_and_breed[key].append(m)

    # ЗАГОЛОВОК ФОРМЫ
    ws.merge_cells('A1:J1')
    ws['A1'] = "ПЕРЕЧЕТНАЯ ВЕДОМОСТЬ"
    ws['A1'].font = Font(bold=True, size=12, name='Times New Roman')
    ws['A1'].alignment = center_alignment

    ws['E2'] = "дата"
    ws['E2'].font = normal_font
    ws['E2'].alignment = center_alignment
    ws['F2'] = list_region.get('date_examination', '')
    ws['F2'].font = normal_font
    ws['F2'].alignment = center_alignment

    ws['A3'] = "Субъект Российской Федерации"
    ws['A3'].font = normal_font
    ws['A3'].alignment = left_alignment
    ws['B3'] = list_region.get('id_district_forestly__id_forestly__id_subject_rf__name_subject_RF', '')
    ws['B3'].font = normal_font
    ws['B3'].alignment = center_alignment
    ws['E3'] = "Лесничество"
    ws['E3'].font = normal_font
    ws['E3'].alignment = center_alignment
    ws['F3'] = list_region.get('id_district_forestly__id_forestly__name_forestly', '')
    ws['F3'].font = normal_font
    ws['F3'].alignment = center_alignment

    ws['A4'] = "Участковое лесничество"
    ws['A4'].font = normal_font
    ws['A4'].alignment = left_alignment
    ws['B4'] = list_region.get('id_district_forestly__name_district_forestly', '')
    ws['B4'].font = normal_font
    ws['B4'].alignment = center_alignment
    ws['E4'] = "Урочище (дача)"
    ws['E4'].font = normal_font
    ws['E4'].alignment = center_alignment
    ws['F4'] = list_region.get('dacha', '0')
    ws['F4'].font = normal_font
    ws['F4'].alignment = center_alignment

    ws['A5'] = "Квартал"
    ws['A5'].font = normal_font
    ws['A5'].alignment = left_alignment
    ws['B5'] = list_region.get('name_quarter', '')
    ws['B5'].font = normal_font
    ws['B5'].alignment = center_alignment
    ws['C5'] = "Выдел"
    ws['C5'].font = normal_font
    ws['C5'].alignment = center_alignment
    ws['D5'] = list_region.get('soil_lot', '')
    ws['D5'].font = normal_font
    ws['D5'].alignment = center_alignment
    ws['E5'] = "Площадь, га"
    ws['E5'].font = normal_font
    ws['E5'].alignment = center_alignment
    ws['F5'] = list_region.get('sample_region', 0)
    ws['F5'].font = normal_font
    ws['F5'].alignment = center_alignment

    # ЗАГОЛОВКИ ТАБЛИЦЫ
    ws.merge_cells('A6:A8')
    ws['A6'] = "Номер ПП/ УО"
    ws['A6'].alignment = center_alignment
    ws['A6'].font = bold_font

    ws.merge_cells('B6:B8')
    ws['B6'] = "Размер ПП (длина, ширина), м, площадь ПП, га/ длина УО, м"
    ws['B6'].alignment = center_alignment
    ws['B6'].font = bold_font

    ws.merge_cells('C6:G6')
    ws['C6'] = "Лесные культуры"
    ws['C6'].alignment = center_alignment
    ws['C6'].font = bold_font

    ws.merge_cells('H6:J6')
    ws['H6'] = "Подрост и молодняк основных лесных древесных пород"
    ws['H6'].alignment = center_alignment
    ws['H6'].font = bold_font

    # Строка 7
    ws.merge_cells('C7:C8')
    ws['C7'] = "порода"
    ws['C7'].alignment = center_alignment
    ws['C7'].font = bold_font

    ws.merge_cells('D7:F7')
    ws['D7'] = "сохранившиеся растения"
    ws['D7'].alignment = center_alignment
    ws['D7'].font = bold_font

    ws.merge_cells('G7:G8')
    ws['G7'] = "погибшие растения, шт."
    ws['G7'].alignment = center_alignment
    ws['G7'].font = bold_font

    ws.merge_cells('H7:H8')
    ws['H7'] = "порода"
    ws['H7'].alignment = center_alignment
    ws['H7'].font = bold_font

    ws.merge_cells('I7:I8')
    ws['I7'] = "кол-во, шт."
    ws['I7'].alignment = center_alignment
    ws['I7'].font = bold_font

    ws.merge_cells('J7:J8')
    ws['J7'] = "высота, м"
    ws['J7'].alignment = center_alignment
    ws['J7'].font = bold_font

    # Строка 8
    ws['D8'] = "кол-во, шт."
    ws['D8'].alignment = center_alignment
    ws['D8'].font = bold_font
    ws['E8'] = "диаметр корневой шейки, мм"
    ws['E8'].alignment = center_alignment
    ws['E8'].font = bold_font
    ws['F8'] = "высота, см"
    ws['F8'].alignment = center_alignment
    ws['F8'].font = bold_font

    # Применяем границы и стили к заголовкам
    for row in range(6, 9):
        for col in range(1, 11):
            cell = ws.cell(row=row, column=col)
            cell.border = thin_border
            cell.font = bold_font
            cell.alignment = center_alignment

    current_row = 9

    if not samples:
        ws.cell(row=current_row, column=1).value = "Нет данных"
        wb.save(output_file)
        return output_file.split("testDjangosite")[1] if "testDjangosite" in output_file else output_file

    all_diameters = []
    all_heights = []
    total_area_sum = 0

    sorted_samples = sorted(samples, key=lambda x: x.get('id', 0))

    # Порядковый номер ПП (начинается с 1)
    pp_number = 1

    for idx, sample in enumerate(sorted_samples):
        sample_id = sample.get('id')
        length = sample.get('length', 0)
        width = sample.get('width', 0)
        area_ha = (length * width) / 10000 if length and width else 0
        total_area_sum += area_ha

        # Получаем списки пород отдельно для культур и для молодняка
        crops_breeds = []
        for key in crops_by_sample_and_breed.keys():
            if key[0] == sample_id:
                crops_breeds.append(key[1])

        molod_breeds = []
        for key in molod_by_sample_and_breed.keys():
            if key[0] == sample_id:
                molod_breeds.append(key[1])

        # Определяем количество строк: максимум из культур и молодняка
        max_rows = max(len(crops_breeds), len(molod_breeds), 1)

        start_row = current_row

        # Для каждой строки выводим культуру и молодняк параллельно
        for i in range(max_rows):
            # Номер ПП и размеры (только в первой строке)
            if i == 0:
                ws.cell(row=current_row, column=1).value = pp_number
                ws.cell(row=current_row, column=1).alignment = center_alignment
                ws.cell(row=current_row, column=1).font = normal_font

                size_text = f"{length}\n{width}\n{round(area_ha, 4)}"
                ws.cell(row=current_row, column=2).value = size_text
                ws.cell(row=current_row, column=2).alignment = center_alignment
                ws.cell(row=current_row, column=2).font = normal_font

            # ЛЕСНЫЕ КУЛЬТУРЫ (колонки C=3, D=4, E=5, F=6, G=7)
            if i < len(crops_breeds):
                breed_id = crops_breeds[i]
                crops_list = crops_by_sample_and_breed.get((sample_id, breed_id), [])
                if crops_list:
                    total_living = sum(c.get('count_living', 0) for c in crops_list)
                    total_dead = sum(c.get('count_dead', 0) for c in crops_list)

                    breed = all_breed.get(breed_id, {})
                    breed_name = breed.get('short_name') or breed.get('name_breed', '')

                    ws.cell(row=current_row, column=3).value = breed_name
                    ws.cell(row=current_row, column=4).value = total_living

                    plants_list = plants_by_sample_and_breed.get((sample_id, breed_id), [])
                    if plants_list:
                        avg_diameter = round(sum(p.get('diameter', 0) for p in plants_list) / len(plants_list), 2)
                        avg_height_plants = round(sum(p.get('height', 0) for p in plants_list) / len(plants_list), 2)

                        ws.cell(row=current_row, column=5).value = avg_diameter
                        ws.cell(row=current_row, column=6).value = avg_height_plants

                        all_diameters.extend([p.get('diameter', 0) for p in plants_list if p.get('diameter')])
                        all_heights.extend([p.get('height', 0) for p in plants_list if p.get('height')])

                    ws.cell(row=current_row, column=7).value = total_dead

                    for col in range(3, 8):
                        ws.cell(row=current_row, column=col).alignment = center_alignment
                        ws.cell(row=current_row, column=col).font = normal_font
            else:
                # Пустые ячейки для культур, если строк меньше
                for col in range(3, 8):
                    ws.cell(row=current_row, column=col).value = ''
                    ws.cell(row=current_row, column=col).alignment = center_alignment
                    ws.cell(row=current_row, column=col).font = normal_font

            # ПОДРОСТ И МОЛОДНЯК (колонки H=8, I=9, J=10)
            if i < len(molod_breeds):
                breed_id = molod_breeds[i]
                molod_list = molod_by_sample_and_breed.get((sample_id, breed_id), [])
                if molod_list:
                    breed = all_breed.get(breed_id, {})
                    breed_name = breed.get('short_name') or breed.get('name_breed', '')

                    ws.cell(row=current_row, column=8).value = breed_name

                    total_count = 0
                    max_h = 0
                    for m in molod_list:
                        total_count += m.get('to0_5', 0)
                        total_count += m.get('from0_6To1_5', 0)
                        total_count += m.get('from1_5', 0)
                        max_h = max(max_h, m.get('max_height', 0))

                    ws.cell(row=current_row, column=9).value = total_count
                    ws.cell(row=current_row, column=10).value = round(max_h, 2) if max_h else ''

                    for col in range(8, 11):
                        ws.cell(row=current_row, column=col).alignment = center_alignment
                        ws.cell(row=current_row, column=col).font = normal_font
            else:
                # Пустые ячейки для молодняка, если строк меньше
                for col in range(8, 11):
                    ws.cell(row=current_row, column=col).value = ''
                    ws.cell(row=current_row, column=col).alignment = center_alignment
                    ws.cell(row=current_row, column=col).font = normal_font

            for col in range(1, 11):
                ws.cell(row=current_row, column=col).border = thin_border

            current_row += 1

        # Объединяем колонки A и B для всех строк этой ПП
        rows_count = current_row - start_row
        if rows_count > 1:
            ws.merge_cells(start_row=start_row, start_column=1, end_row=current_row - 1, end_column=1)
            ws.merge_cells(start_row=start_row, start_column=2, end_row=current_row - 1, end_column=2)

        # Увеличиваем порядковый номер для следующей ПП
        pp_number += 1

    # ИТОГИ
    avg_diameter = round(sum(all_diameters) / len(all_diameters), 2) if all_diameters else 0
    avg_height = round(sum(all_heights) / len(all_heights), 2) if all_heights else 0

    total_crops_count = sum(c.get('count_living', 0) for c in crops)
    total_crops_dead = sum(c.get('count_dead', 0) for c in crops)
    total_young_count = sum(m.get('to0_5', 0) + m.get('from0_6To1_5', 0) + m.get('from1_5', 0) for m in molod)

    # Строка "Всего" - жирный шрифт, с двумя знаками после запятой
    ws.cell(row=current_row, column=1).value = "Всего"
    ws.cell(row=current_row, column=2).value = round(total_area_sum, 4)
    ws.cell(row=current_row, column=4).value = total_crops_count
    ws.cell(row=current_row, column=5).value = avg_diameter
    ws.cell(row=current_row, column=6).value = avg_height
    ws.cell(row=current_row, column=7).value = total_crops_dead
    ws.cell(row=current_row, column=9).value = total_young_count

    for col in range(1, 11):
        cell = ws.cell(row=current_row, column=col)
        cell.border = thin_border
        cell.alignment = center_alignment
        cell.font = bold_font  # Жирный шрифт для всей строки "Всего"
    current_row += 1

    # Строка "Итого на 1 га" - жирный шрифт, округление до целых
    if total_area_sum > 0:
        ws.cell(row=current_row, column=1).value = "Итого на 1 га"
        ws.cell(row=current_row, column=4).value = round(total_crops_count / total_area_sum, 0)
        ws.cell(row=current_row, column=7).value = round(total_crops_dead / total_area_sum, 0)
        ws.cell(row=current_row, column=9).value = round(total_young_count / total_area_sum, 0)
        for col in range(1, 11):
            cell = ws.cell(row=current_row, column=col)
            cell.border = thin_border
            cell.alignment = center_alignment
            cell.font = bold_font  # Жирный шрифт для всей строки "Итого на 1 га"

    # Ширина колонок
    column_widths = {'A': 10, 'B': 22, 'C': 12, 'D': 10, 'E': 18, 'F': 12, 'G': 12, 'H': 12, 'I': 10, 'J': 10}
    for col, width in column_widths.items():
        ws.column_dimensions[col].width = width

    ws.row_dimensions[1].height = 25
    ws.row_dimensions[2].height = 20
    ws.row_dimensions[6].height = 35
    ws.row_dimensions[7].height = 25
    ws.row_dimensions[8].height = 25

    os.makedirs(os.path.dirname(output_file), exist_ok=True)
    wb.save(output_file)
    print(f"Файл сохранен: {os.path.abspath(output_file)}")

    if "testDjangosite" in output_file:
        return output_file.split("testDjangosite")[1]
    return output_file