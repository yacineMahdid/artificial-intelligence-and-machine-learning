def displayPathtoPrincess(N, grid):
    '''output a big string with LEFT, RIGHT, UP, DOWN corresponding to path to princess'''
    
    # Find where is p
    # TOP_LEFT [0][0]
    # TOP_RIGHT [0][N-1]
    # BOTTOM_LEFT [N-1][0]
    # BOTTOM_RIGHT [N-1][N-1]
    path = ""
    for end_row in [0, N-1]:
        for end_col in [0, N-1]:
            if grid[end_row][end_col] == 'p':
                
                # TOP_LEFT
                if end_row == 0 and end_col == 0:
                    path = (N//2)*"UP\n" + (N//2)*"LEFT\n"
                
                # TOP_RIGHT
                if end_row == 0 and end_col == (N-1):
                    path = (N//2)*"UP\n" + (N//2)*"RIGHT\n"
                
                # DOWN_LEFT
                if end_row == (N-1) and end_col == 0:
                    path = (N//2)*"DOWN\n" + (N//2)*"LEFT\n"
                
                # DOWN_RIGHT
                if end_row == (N-1) and end_col == (N-1):
                    path = (N//2)*"DOWN\n" + (N//2)*"RIGHT\n"
                
    return path
                
                
# Get N
N = int(input())

grid = []
# Iteratively Get the character grid
for _ in range(N):
    line = list(input())
    grid.append(line)

path = displayPathtoPrincess(N, grid)    

print(path)