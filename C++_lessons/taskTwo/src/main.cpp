#include <iostream>
using namespace std;

void transpose(int x, int y) {
    int trans[y][x];
    cout << "Транспонированная матрица:" << endl;
    for (int i = 0; i < y; i++) {
        for (int j = 0; j < x; j++) {
            cout << trans[j][i] << " ";
        }
        cout << endl;
    }
}

int main() {
    int x, y;
    cout << "Введите x (количество строк): ";
    cin >> x;
    cout << "Введите y (количество столбцов): ";
    cin >> y;
    
    int arr[x][y]; // Простой массив
    
    for (int i = 0; i < x; i++) {
        for (int j = 0; j < y; j++) {
            cout << "Введите arr[" << i << "][" << j << "] (элемент матрицы): ";
            cin >> arr[i][j];
        }
    }
    
    transpose(x, y);
    return 0;
}

