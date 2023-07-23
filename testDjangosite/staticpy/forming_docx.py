from testDjangosite.settings import BASE_DIR
from docxtpl import DocxTemplate
import uuid
# название включает себя fieldcard_{id}
# название описание участка тоже самое
# две новых таблицы для хранения ссылок на полевую карточку и описание участка

def forming_docx_fieldcard(context: dict):
    doc = DocxTemplate(f"{BASE_DIR}\\fieldcard.docx")
    doc.render(context)
    filepath = f"{BASE_DIR}\\media\\docx_files\\fieldcards\\fieldcard_{context['id']}.docx"
    doc.save(filepath)
    print(filepath.split("testDjangosite"))
    return filepath.split("testDjangosite")[1]


def forming_docx_desc_regiom(context: dict):
    pass
