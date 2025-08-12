import requests

# ===== CONFIGURAÇÕES =====
GITHUB_TOKEN = "SEU_TOKEN_AQUI"  # Token com permissão 'repo'
OWNER_ORIGEM = "usuario_origem"
REPO_ORIGEM = "repo_origem"

DESTINOS = [
    ("ufpb-aps-poo", "projeto-aps-codeon"),
    ("ufpb-aps-poo", "projeto-aps-nexa"), 
    ("ufpb-aps-poo", "projeto-aps-byteplan"), 
    ("ufpb-aps-poo", "projeto-aps-chip"), 
    ("ufpb-aps-poo", "projeto-aps-dataforge"), 
    ("ufpb-aps-poo", "projeto-aps-5up"),
]

# Cabeçalhos para autenticação
headers = {
    "Authorization": f"token {GITHUB_TOKEN}",
    "Accept": "application/vnd.github.v3+json"
}


def buscar_issues(owner, repo):
    """Busca todas as issues (exceto PRs) de um repositório"""
    url = f"https://api.github.com/repos/{owner}/{repo}/issues"
    params = {"state": "all", "per_page": 100}
    issues = []
    while url:
        r = requests.get(url, headers=headers, params=params)
        r.raise_for_status()
        data = r.json()
        issues.extend([i for i in data if "pull_request" not in i])
        # Verifica se tem próxima página
        url = r.links.get("next", {}).get("url")
        params = None  # já está na URL
    return issues


def criar_issue(owner, repo, titulo, corpo, labels):
    """Cria uma issue em um repositório"""
    url = f"https://api.github.com/repos/{owner}/{repo}/issues"
    payload = {
        "title": titulo,
        "body": corpo,
        "labels": labels
    }
   
    r = requests.post(url, headers=headers, json=payload)
    if r.status_code == 201:
        print(f"✅ Issue '{titulo}' criada em {owner}/{repo}")
    else:
        print(f"⚠️ Erro ao criar '{titulo}' em {owner}/{repo}: {r.status_code} - {r.text}")


def main():
    # 1. Buscar issues da origem
    issues_origem = buscar_issues(OWNER_ORIGEM, REPO_ORIGEM)
    print(f"Encontradas {len(issues_origem)} issues na origem.")

    # 2. Para cada repositório de destino
    for owner_dest, repo_dest in DESTINOS:
        print(f"\n📦 Copiando para {owner_dest}/{repo_dest}...")
        issues_dest = buscar_issues(owner_dest, repo_dest)
        titulos_dest = {i["title"] for i in issues_dest}

        # 3. Copiar apenas as que não existem no destino
        for issue in issues_origem:
            if issue["title"] not in titulos_dest:
                criar_issue(
                    owner_dest,
                    repo_dest,
                    issue["title"],
                    issue["body"] or "",
                    [label["name"] for label in issue["labels"]]
                )
            else:
                print(f"⏭️ Ignorando '{issue['title']}' (já existe no destino)")


if __name__ == "__main__":
    main()
