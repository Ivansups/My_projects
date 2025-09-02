from typing import List


class Solution:
    def countSquares(self, matrix: List[List[int]]) -> int:
        res = [0, 0, 0, 0]
        for i in matrix:
            res[0] += sum(i)
            