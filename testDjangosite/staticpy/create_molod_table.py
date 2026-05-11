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
    header_fill = PatternFill(start_color="D9E1F2", end_color="D9E1F2", fill_type="solid")
    gap_fill = PatternFill(start_color="D9E1F2", end_color="D9E1F2", fill_type="solid")  # Тот же цвет для промежутков
    bold_font = Font(bold=True)


    list_region = json_data.get('list_region', [{}])[0] if json_data.get('list_region') else {}
    output_file = f'{BASE_DIR}/media/excel_files/fc/list_region_{list_region.get("id")}.xlsx'
    samples = json_data.get('samples', [])
    molod = json_data.get('molod', [])
    crops = json_data.get('crops', [])
    plants = json_data.get('plants', [])
    all_breed = {b['id']: b for b in json_data.get('all_breed', [])}


    crops_by_sample = defaultdict(list)
    for c in crops:
        crops_by_sample[c.get('id_sample_id')].append(c)

    plants_by_sample = defaultdict(list)
    for p in plants:
        plants_by_sample[p.get('id_sample_id')].append(p)

    molod_by_sample_and_breed = defaultdict(list)
    for m in molod:
        molod_by_sample_and_breed[(m.get('id_sample_id'), m.get('id_breed_id'))].append(m)

    # хэдр
    ws.merge_cells('A1:J1')
    ws['A1'] = "ФОРМА ПЕРЕЧЕТНОЙ ВЕДОМОСТИ"
    ws['A1'].font = Font(bold=True, size=14)
    ws['A1'].alignment = center_alignment

    ws['E2'] = "дата"
    ws['E2'].alignment = center_alignment
    ws['F2'] = list_region.get('date_examination', '')
    ws['F2'].alignment = center_alignment

    ws['A3'] = "Субъект Российской Федерации"
    ws['A3'].alignment = left_alignment
    ws['B3'] = list_region.get('id_district_forestly__id_forestly__id_subject_rf__name_subject_RF', '')
    ws['B3'].alignment = center_alignment
    ws['E3'] = "Лесничество"
    ws['E3'].alignment = center_alignment
    ws['F3'] = list_region.get('id_district_forestly__id_forestly__name_forestly', '')
    ws['F3'].alignment = center_alignment

    ws['A4'] = "Участковое лесничество"
    ws['A4'].alignment = left_alignment
    ws['B4'] = list_region.get('id_district_forestly__name_district_forestly', '')
    ws['B4'].alignment = center_alignment
    ws['E4'] = "Урочище (дача)"
    ws['E4'].alignment = center_alignment
    ws['F4'] = list_region.get('dacha', '0')
    ws['F4'].alignment = center_alignment

    ws['A5'] = "Квартал"
    ws['A5'].alignment = left_alignment
    ws['B5'] = list_region.get('name_quarter', '')
    ws['B5'].alignment = center_alignment
    ws['C5'] = "Выдел"
    ws['C5'].alignment = center_alignment
    ws['D5'] = list_region.get('soil_lot', '')
    ws['D5'].alignment = center_alignment
    ws['E5'] = "Площадь, га"
    ws['E5'].alignment = center_alignment
    ws['F5'] = list_region.get('sample_region', 0)
    ws['F5'].alignment = center_alignment

    ws['A6'] = "размеры пробных площадей"
    ws['A6'].font = bold_font
    ws['A6'].alignment = center_alignment

    # заголовки таблицы
    ws.merge_cells('A7:A9')
    ws['A7'] = "Номер ПП/ УО"
    ws['A7'].alignment = center_alignment
    ws['A7'].font = bold_font

    ws.merge_cells('B7:B9')
    ws['B7'] = "Размер ПП (длина, ширина), м, площадь ПП, га/ длина УО, м"
    ws['B7'].alignment = center_alignment
    ws['B7'].font = bold_font

    ws.merge_cells('C7:G7')
    ws['C7'] = "Лесные культуры"
    ws['C7'].alignment = center_alignment
    ws['C7'].font = bold_font

    ws.merge_cells('H7:J7')
    ws['H7'] = "Подрост и молодняк основных лесных древесных пород"
    ws['H7'].alignment = center_alignment
    ws['H7'].font = bold_font

    ws['C8'] = "пород"
    ws['C8'].alignment = center_alignment
    ws.merge_cells('D8:F8')
    ws['D8'] = "сохранившиеся растения"
    ws['D8'].alignment = center_alignment
    ws['G8'] = "погибшие растения, шт."
    ws['G8'].alignment = center_alignment
    ws['H8'] = "порода"
    ws['H8'].alignment = center_alignment
    ws['I8'] = "кол-во, шт."
    ws['I8'].alignment = center_alignment
    ws['J8'] = "высота, м *"
    ws['J8'].alignment = center_alignment

    ws['D9'] = "кол-во, шт."
    ws['D9'].alignment = center_alignment
    ws['E9'] = "диаметр корневой шейки, мм *"
    ws['E9'].alignment = center_alignment
    ws['F9'] = "высота, см *"
    ws['F9'].alignment = center_alignment

    for row in range(7, 10):
        for col in range(1, 11):
            cell = ws.cell(row=row, column=col)
            cell.border = thin_border
            cell.fill = header_fill
            cell.font = bold_font
            cell.alignment = center_alignment


    current_row = 10

    if not samples:
        ws.cell(row=current_row, column=1).value = "Нет данных"
        wb.save(output_file)
        print(f"✅ Файл {output_file} создан (нет данных)")
        return output_file


    all_diameters = []
    all_heights = []
    total_area_sum = 0

    for idx, sample in enumerate(samples):
        sample_id = sample.get('id')
        length = sample.get('length', 0)
        width = sample.get('width', 0)
        area_ha = (length * width) / 10000 if length and width else 0
        total_area_sum += area_ha

        sample_crops = crops_by_sample.get(sample_id, [])
        sample_plants = plants_by_sample.get(sample_id, [])


        molod_breeds = set()
        for key in molod_by_sample_and_breed.keys():
            if key[0] == sample_id:
                molod_breeds.add(key[1])


        num_rows = max(len(sample_crops), len(molod_breeds), 1)
        start_row = current_row

        for row_idx in range(num_rows):
            # номер пп + размеры
            if row_idx == 0:
                ws.cell(row=current_row, column=1).value = sample_id
                ws.cell(row=current_row, column=1).alignment = center_alignment
                ws.cell(row=current_row, column=2).value = f"{length}\n{width}\n{area_ha:.4f}"
                ws.cell(row=current_row, column=2).alignment = center_alignment

            # тут лесные культуры
            if row_idx < len(sample_crops):
                crop = sample_crops[row_idx]
                breed_id = crop.get('id_breed_id')
                breed = all_breed.get(breed_id, {})

                ws.cell(row=current_row, column=3).value = breed.get('short_name') or breed.get('name_breed', '')
                ws.cell(row=current_row, column=3).alignment = center_alignment

                ws.cell(row=current_row, column=4).value = crop.get('count_living', 0)
                ws.cell(row=current_row, column=4).alignment = center_alignment


                matching_plants = [p for p in sample_plants if p.get('id_breed_id') == breed_id]
                if matching_plants:
                    plant = matching_plants[0]
                    diameter = plant.get('diameter', 0)
                    height = plant.get('height', 0)
                    ws.cell(row=current_row, column=5).value = diameter
                    ws.cell(row=current_row, column=5).alignment = center_alignment
                    ws.cell(row=current_row, column=6).value = height
                    ws.cell(row=current_row, column=6).alignment = center_alignment


                    if diameter:
                        all_diameters.append(diameter)
                    if height:
                        all_heights.append(height)

                ws.cell(row=current_row, column=7).value = crop.get('count_dead', 0)
                ws.cell(row=current_row, column=7).alignment = center_alignment

            # тут рисуется молодняк
            if row_idx < len(molod_breeds):
                breed_id = list(molod_breeds)[row_idx]
                breed = all_breed.get(breed_id, {})
                molod_list = molod_by_sample_and_breed.get((sample_id, breed_id), [])

                if molod_list:
                    ws.cell(row=current_row, column=8).value = breed.get('short_name') or breed.get('name_breed', '')
                    ws.cell(row=current_row, column=8).alignment = center_alignment

                    total_count = 0
                    for m in molod_list:
                        total_count += m.get('to0_5', 0)
                        total_count += m.get('from0_6To1_5', 0)
                        total_count += m.get('from1_5', 0)
                    ws.cell(row=current_row, column=9).value = total_count
                    ws.cell(row=current_row, column=9).alignment = center_alignment

                    max_h = max((m.get('max_height', 0) for m in molod_list), default=0)
                    if max_h:
                        ws.cell(row=current_row, column=10).value = max_h
                        ws.cell(row=current_row, column=10).alignment = center_alignment

            for col in range(1, 11):
                ws.cell(row=current_row, column=col).border = thin_border
            current_row += 1

        if num_rows > 1:
            ws.merge_cells(start_row=start_row, start_column=1, end_row=current_row - 1, end_column=1)
            ws.merge_cells(start_row=start_row, start_column=2, end_row=current_row - 1, end_column=2)
        # это цветной разделитель между ячейками сампла
        if idx < len(samples) - 1:
            for col in range(1, 11):
                cell = ws.cell(row=current_row, column=col)
                cell.value = ""
                cell.border = thin_border
                cell.fill = gap_fill
                cell.alignment = center_alignment
            current_row += 1

    # ========== ИТОГИ ==========
    avg_diameter = sum(all_diameters) / len(all_diameters) if all_diameters else 0
    avg_height = sum(all_heights) / len(all_heights) if all_heights else 0

    total_crops_count = sum(c.get('count_living', 0) for c in crops)
    total_crops_dead = sum(c.get('count_dead', 0) for c in crops)
    total_young_count = sum(m.get('to0_5', 0) + m.get('from0_6To1_5', 0) + m.get('from1_5', 0) for m in molod)

    ws.cell(row=current_row, column=1).value = "Всего"
    ws.cell(row=current_row, column=2).value = round(total_area_sum, 4) if total_area_sum else 0
    ws.cell(row=current_row, column=4).value = total_crops_count if total_crops_count else 0
    ws.cell(row=current_row, column=5).value = round(avg_diameter, 2) if avg_diameter else 0
    ws.cell(row=current_row, column=6).value = round(avg_height, 2) if avg_height else 0
    ws.cell(row=current_row, column=7).value = total_crops_dead if total_crops_dead else 0
    ws.cell(row=current_row, column=9).value = total_young_count if total_young_count else 0

    for col in range(1, 11):
        ws.cell(row=current_row, column=col).border = thin_border
        ws.cell(row=current_row, column=col).alignment = center_alignment
        if col in [1, 2, 4, 5, 6, 7, 9]:
            ws.cell(row=current_row, column=col).font = bold_font
    current_row += 1

    if total_area_sum > 0:
        ws.cell(row=current_row, column=1).value = "Итого на 1 га"
        ws.cell(row=current_row, column=4).value = round(total_crops_count / total_area_sum,
                                                         2) if total_crops_count else 0
        ws.cell(row=current_row, column=7).value = round(total_crops_dead / total_area_sum,
                                                         2) if total_crops_dead else 0
        ws.cell(row=current_row, column=9).value = round(total_young_count / total_area_sum,
                                                         2) if total_young_count else 0
        for col in range(1, 11):
            ws.cell(row=current_row, column=col).border = thin_border
            ws.cell(row=current_row, column=col).alignment = center_alignment
        current_row += 1


    if total_crops_count > 0:
        ws.cell(row=current_row, column=1).value = "%"
        surviving = (total_crops_count - total_crops_dead) / total_crops_count * 100
        ws.cell(row=current_row, column=4).value = round(surviving, 2)
        for col in range(1, 11):
            ws.cell(row=current_row, column=col).border = thin_border
            ws.cell(row=current_row, column=col).alignment = center_alignment


    for col, width in {'A': 12, 'B': 18, 'C': 8, 'D': 10, 'E': 15, 'F': 12, 'G': 15, 'H': 10, 'I': 12, 'J': 12}.items():
        ws.column_dimensions[col].width = width

    ws.row_dimensions[1].height = 25
    ws.row_dimensions[2].height = 20
    ws.row_dimensions[7].height = 30
    ws.row_dimensions[8].height = 25
    ws.row_dimensions[9].height = 25
    ws.freeze_panes = 'A10'

    wb.save(output_file)
    print(f"Файл сохранен: {os.path.abspath(output_file)}")
    return output_file.split("testDjangosite")[1]