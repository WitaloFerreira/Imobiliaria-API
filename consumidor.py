import requests

URL_FILA = "http://localhost:8080/contratos/fila"

def processar_mensagens():
    print("Consumidor iniciado. Verificando fila...")
    try:
        response = requests.get(URL_FILA)
        if response.status_code == 200:
            mensagens = response.json()
            if mensagens:
                print(f"[{len(mensagens)}] Mensagens encontradas na fila:")
                for msg in mensagens:
                    print(f" -> Processando: {msg}")
            else:
                print("Fila vazia. Nenhuma mensagem retida.")
        else:
            print(f"Erro na requisição: {response.status_code}")
    except Exception as e:
        print("Erro ao conectar com o servidor.")

if __name__ == "__main__":
    processar_mensagens()