const BASE_URL = "http://localhost:8080";
const HEADERS = { "Content-Type": "application/json" };

async function dispararRequisicao(passo: string, endpoint: string, metodo: string, corpo?: any) {
    const opcoes: RequestInit = { method: metodo, headers: HEADERS };
    if (corpo) opcoes.body = JSON.stringify(corpo);

    try {
        const response = await fetch(`${BASE_URL}${endpoint}`, opcoes);
        if (passo !== "Oculto") {
            console.log(`\n--- ${passo} ---`);
            console.log(`Status Code: ${response.status}`);
            const contentType = response.headers.get("content-type");
            if (contentType && contentType.includes("application/json")) {
                const json = await response.json();
                console.log(JSON.stringify(json, null, 2));
            } else {
                console.log("Resposta do Servidor:", await response.text());
            }
        }
    } catch (error) {
        console.error("Erro na conexão:", error);
    }
}

async function testarCenarioTS() {
    console.log("Iniciando cenário TYPESCRIPT...\n");

    // 1. Clientes
    const reqCarlos = { id: 30, nome: "Carlos Mendes", email: "carlos@email.com" };
    const reqAna = { id: 40, nome: "Ana Costa", email: "ana@email.com" };
    
    await dispararRequisicao("Oculto", "/clientes", "POST", reqCarlos);
    await dispararRequisicao("Oculto", "/clientes", "POST", reqAna);
    console.log("Clientes do TS (Carlos e Ana) criados.");

    // 2. Imóvel (Apto pertence à Ana ID 40)
    const reqImovel = {
        id: 102,
        valor: 280000.0,
        tamanho: 85.0,
        endereco: { rua: "Avenida Central", numero: 456, bairro: "Planalto", cidade: "Quixadá" },
        proprietario: { id: 40 }
    };
    await dispararRequisicao("Oculto", "/imoveis", "POST", reqImovel);
    console.log("Imóvel do TS (ID 102) criado.");

    // 3. Contrato (Carlos ID 30 aluga o Apto ID 102)
    const reqContrato = {
        id: 2,
        valorMensal: 1200.0,
        locatario: { id: 30 },
        propriedade: { id: 102 }
    };
    
    await dispararRequisicao("Contrato do TS (ID 2) finalizado", "/contratos", "POST", reqContrato);
    await dispararRequisicao("Consultando TODOS os Contratos da Memória", "/contratos", "GET");
}

testarCenarioTS();