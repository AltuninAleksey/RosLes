from testDjangosite.settings import BASE_DIR
from docxtpl import DocxTemplate
import os
import uuid
# название включает себя fieldcard_{id}
# название описание участка тоже самое
# две новых таблицы для хранения ссылок на полевую карточку и описание участка

def forming_docx_fieldcard(context: dict):
    doc = DocxTemplate(os.path.abspath(f"{BASE_DIR}\\media\\fieldcard.docx"))
    doc.render(context)
    filepath = os.path.abspath(f"{BASE_DIR}\\media\\docx_files\\fieldcards\\fieldcard_{context['id']}.docx")
    doc.save(filepath)
    return filepath.split("testDjangosite")[1]


def forming_docx_desc_region(context: dict):
    doc = DocxTemplate(os.path.abspath(f"{BASE_DIR}\\media\\desc_region.docx"))
    doc.render(context)
    filepath = os.path.abspath(f"{BASE_DIR}\\media\\docx_files\\desc_region\\desc_region_{context['id']}.docx")
    return filepath.split("testDjangosite")[1]
