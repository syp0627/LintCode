M
1526572446
tags: Tree, Hash Map, DFS

给一串3-digit 的数组. 每个数字的表达一个TreeNode, 3 digit分别代表: depth.position.value

这串数字已经从小到大排列. 求: 所有可能的 root->leaf path 的所有可能的 path sum 总和. 

#### DFS, Hash Map
- 因为前两个digit可以locate一个node, 所以可以把前两个digit作为key, 定位node.
- 特点: 比如考虑root, 有 n 个leaf, 就会加 n 遍root, 因为有 n 个 unique path嘛.
- 对于每一个node也是一样: 只要有child, 到这个node位置的以上path sum 就要被重加一次.
- format: depth.position.value. (on same level, position may not be continuous)
- approach: map each number into: <depth.position, value>, and dfs. 
- Start from dfs(map, rootKey, sum):
- 1. add node value to sum
- 2. compute potential child.
- 3. check child existence, if exist, add sum to result (for both left/right child). Check existence using the map.
= 4. also, if child exist, dfs into next level

```
/*
If the depth of a tree is smaller than 5, 
then this tree can be represented by a list of three-digits integers.

For each integer in this list:
The hundreds digit represents the depth D of this node, 1 <= D <= 4.
The tens digit represents the position P of this node in the level it belongs to, 1 <= P <= 8.
The position is the same as that in a full binary tree.

The units digit represents the value V of this node, 0 <= V <= 9.
Given a list of ascending three-digits integers representing a binary with the depth smaller than 5. 
You need to return the sum of all paths from the root towards the leaves.

Example 1:
Input: [113, 215, 221]
Output: 12
Explanation: 
The tree that the list represents is:
    3
   / \
  5   1

The path sum is (3 + 5) + (3 + 1) = 12.
Example 2:
Input: [113, 221]
Output: 4
Explanation: 
The tree that the list represents is: 
    3
     \
      1

The path sum is (3 + 1) = 4.

*/

/*
Thoughts:
Goal: find all paths sum
format: depth.position.value. (on same level, position may not be continuous)
approach:
map each number into: <depth.position, value>
start from dfs(map, rootKey, sum):
1. add node value to sum
2. compute potential child.
3. check child existence, if exist, add sum to result (for both left/right child). Check existence using the map.
4. also, if child exist, dfs into next level

int dfs(num, )
*/


class Solution {
    public int pathSum(int[] nums) {
        // edge case
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // build map
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            int key = num / 10; // joining depth+position
            int value = num % 10;
            map.put(key, value);
        }

        // dfs, return result    
        return dfs(map, nums[0] / 10, 0);
    }

    private int dfs(Map<Integer, Integer> map, int key, int sum) {
        int result = 0;
        // add curr value to sum
        sum += map.get(key);

        // compute left/right child
        int level = key / 10;
        int position = key % 10;
        int leftChildKey = (level + 1) * 10 + position * 2 - 1;
        int rightChildKey = leftChildKey + 1;
    
        // if child exist, add sum to result
        if (!map.containsKey(leftChildKey) && !map.containsKey(rightChildKey)) {
            result += sum;
            return result;
        }

        // dfs on child, if applicable
        if (map.containsKey(leftChildKey)) 
            result += dfs(map, leftChildKey, sum);
        if (map.containsKey(rightChildKey)) 
            result += dfs(map, rightChildKey, sum);

        return result;
    }
}

```