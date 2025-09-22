def read_matrix(filename):
    """Читает матрицу из файла input.txt"""
    with open(filename, 'r') as file:
        lines = file.readlines()
    
    matrix = []
    for line in lines:
        row = [int(x) for x in line.strip().split()]
        matrix.append(row)
    
    return matrix

def determinant(matrix):
    """Вычисляет определитель матрицы рекурсивно"""
    n = len(matrix)
    
    if n == 1:
        return matrix[0][0]
    
    if n == 2:
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]
    
    det = 0
    for j in range(n):
        minor = []
        for i in range(1, n):
            row = []
            for k in range(n):
                if k != j:
                    row.append(matrix[i][k])
            minor.append(row)
        
        sign = (-1) ** j
        det += sign * matrix[0][j] * determinant(minor)
    
    return det

def write_result(filename, result):
    """Записывает результат в файл output.txt"""
    with open(filename, 'w') as file:
        file.write(str(result))

def main():
    try:
        matrix = read_matrix('input.txt')
        det = determinant(matrix)
        write_result('output.txt', det)
        print(f"Определитель матрицы: {det}")
    except FileNotFoundError:
        print("Файл input.txt не найден!")
    except Exception as e:
        print(f"Ошибка: {e}")

if __name__ == "__main__":
    main()
