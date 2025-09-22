#include <iostream>
#include <string>
using namespace std;

int validate() {
    int number;
    while (true) {
        cin >> number;
        if (cin.fail()) {
            cin.clear();
            cin.ignore(1000, '\n');
            cout << "Ошибка: введите целое число!" << endl;
            continue;
        }
        break;
    }
    return number;
}

void transpose(string** arr, int ]x, int y) {
    cout << "\nТранспонированная матрица:" << endl;
    for (int i = 0; i < y; i++) {
        for (int j = 0; j < x; j++) {
            cout << arr[j][i] << " ";
        }
        cout << endl;
    }
}

int main() {
    int x, y;
    
    cout << "Введите x (количество строк): ";
    x = validate();
    
    cout << "Введите y (количество столбцов): ";
    y = validate();
    
    string** arr = new string*[x];
    for(int i = 0; i < x; i++) {
        arr[i] = new string[y];
    }
    
    for (int i = 0; i < x; i++) {
        for (int j = 0; j < y; j++) {
            cout << "Введите arr[" << i << "][" << j << "]: ";
            cin >> arr[i][j];
        }
    }

    cout << "\nИсходная матрица:" << endl;
    for (int i = 0; i < x; i++) {
        for (int j = 0; j < y; j++) {
            cout << arr[i][j] << " ";
        }
        cout << endl;
    }

    
    transpose(arr, x, y);
    
    for(int i = 0; i < x; i++) {
        delete[] arr[i];
    }
    delete[] arr;
    
    return 0;
}

