import requests
import json

BASE_URL = "http://localhost:8080"
HEADERS = {"Content-Type": "application/json"}

def imprimir_resposta(passo, response):
    print(f"\n--- {passo} ---")
    print(f"Status Code: {response.status_code}")
    try:
        print(json.dumps(response.json(), indent=2, ensure_ascii=False))
    except:
        print("Resposta do Servidor:", response.text)

def testar_cenario_python():
    print("Iniciando cenário PYTHON...")

    # 1. Clientes
    req_joao = {"id": 10, "nome": "João Silva", "email": "joao@email.com"}
    req_maria = {"id": 20, "nome": "Maria Oliveira", "email": "maria@email.com"}
    
    requests.post(f"{BASE_URL}/clientes", json=req_joao, headers=HEADERS)
    requests.post(f"{BASE_URL}/clientes", json=req_maria, headers=HEADERS)
    print("Clientes do Python (João e Maria) criados.")

    # 2. Imóvel (Casa pertence ao João ID 10)
    req_imovel = {
        "id": 101,
        "valor": 350000.0,
        "tamanho": 120.5,
        "endereco": { "rua": "Rua das Flores", "numero": 123, "bairro": "Centro", "cidade": "Quixadá" },
        "proprietario": { "id": 10 } 
    }
    requests.post(f"{BASE_URL}/imoveis", json=req_imovel, headers=HEADERS)
    print("Imóvel do Python (ID 101) criado.")

    # 3. Contrato (Maria ID 20 aluga a Casa ID 101)
    req_contrato = {
        "id": 1,
        "valorMensal": 1500.0,
        "locatario": { "id": 20 },
        "propriedade": { "id": 101 }
    }
    res_contrato = requests.post(f"{BASE_URL}/contratos", json=req_contrato, headers=HEADERS)
    imprimir_resposta("Contrato do Python (ID 1) finalizado", res_contrato)
    
    res_get = requests.get(f"{BASE_URL}/contratos")
    imprimir_resposta("Consultando TODOS os Contratos da Memória", res_get)

if __name__ == "__main__":
    testar_cenario_python()