class Solution:
    def isMatch(self, s: str, p: str) -> bool:
        if (len(s) != len(p)):
            return False
        for i in range(len(p)):
            if (s[i] != p[i] and (p[i] not in ['*', '.'])):
                return False
            simP, simJ = [], []
            for j in range(i, len(p)):
                if p[i] != p[j]:
                    break
                if p[i] == p[j]:
                    simP.append(p[i])
                    simJ.append(s[i])
            if set(simP != simJ):
                