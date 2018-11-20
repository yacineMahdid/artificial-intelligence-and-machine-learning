import torch
from torch.autograd import Variable
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
from torchvision import datasets, transforms

model = TheModelClass(*args, **kwargs)
model.load_state_dict(torch.load(PATH))
model.eval()