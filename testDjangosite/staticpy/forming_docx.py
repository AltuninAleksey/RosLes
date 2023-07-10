from testDjangosite.settings import BASE_DIR
from docxtpl import DocxTemplate
import uuid
# название включает себя fieldcard_{id}
# название описание участка тоже самое
# две новых таблицы для хранения ссылок на полевую карточку и описание участка

def forming_docx_fieldcard(context: dict):
    doc = DocxTemplate("D:\\pythonProject\\Pavel\\RosLes\\testDjangosite\\fieldcard.docx")
    doc.render(context)
    filepath = f"{BASE_DIR}\\docx_files\\{str(uuid.uuid4())}.docx"
    doc.save(filepath)
    return filepath
