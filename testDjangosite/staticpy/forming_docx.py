from testDjangosite.settings import BASE_DIR
from docxtpl import DocxTemplate
import uuid

def forming_docx_fieldcard(context: dict):
    doc = DocxTemplate("D:\\pythonProject\\Pavel\\RosLes\\testDjangosite\\fieldcard.docx")
    doc.render(context)
    filepath = f"{BASE_DIR}\\docx_files\\{str(uuid.uuid4())}.docx"
    doc.save(filepath)
    return filepath
