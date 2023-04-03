#include <bits/stdc++.h>
using namespace std;
list<int> adj[20005];
vector<bool> visited(20005, 0);
int cur_max = 0;
void dfs(int cur, int depth) {

    if(visited[cur]) return;
    visited[cur] = 1;
    cur_max = max(cur_max, depth);
    for(auto it: adj[cur]) {
        dfs(it, depth + 1);
    }
    visited[cur] = 0;
}

int main(void) {
    int time;
    cin >> time;
    while(time--) {
        int n;
        cin >> n;
        for(int i = 1; i <= n; i++) {
            adj[i].clear();
        }
        cur_max = 0;
        for(int i = 0; i < n - 1; i++) {
            int a, b;
            cin >> a >> b;
            adj[a].push_back(b);
            adj[b].push_back(a);
        }
        for(int i = 1; i <= n; i++) {
            dfs(i, 0);
        }
        cout << cur_max << endl;
    }
}