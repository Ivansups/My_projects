from re import match
from typing import List
from unittest import case


class Solution:
    def findMedianSortedArrays(self, nums1: List[int], nums2: List[int]) -> float:
        res = nums1 + nums2
        res = sorted(res)
        if len(res) % 2 == 0:
            return(float(res[int(len(res) / 2) - 1] + res[int(len(res) / 2)]) / 2)
        else:
            return(float(res[int(len(res) / 2)]))